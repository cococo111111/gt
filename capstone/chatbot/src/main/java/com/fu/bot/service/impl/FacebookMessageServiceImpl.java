package com.fu.bot.service.impl;

import com.fu.bot.model.*;
import com.fu.database.model.SaveData;
import com.fu.nlp.service.AccentizerService;
import com.fu.bot.service.FacebookAPIService;
import com.fu.bot.service.FacebookMessageService;
import com.fu.bot.utils.Constant;
import com.fu.bot.utils.FBChatHelper;
import com.fu.cache.client.JedisClient;
import com.fu.common.constant.KeyConstant;
import com.fu.common.util.AESUtil;
import com.fu.common.util.DateUtil;
import com.fu.database.dao.*;
import com.fu.database.entity.*;
import com.fu.nlp.service.NaturalLanguageProcessingService;
import com.fu.notification.service.FCMService;
import com.fu.vision.service.VisionService;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.*;


/**
 * Created by manlm on 9/3/2016.
 */
@Service
public class FacebookMessageServiceImpl implements FacebookMessageService {

    private static final Logger LOG = Logger.getLogger(FacebookMessageServiceImpl.class);

    private final Properties properties;

    private final FacebookAPIService facebookAPIService;

    private final FBChatHelper helper;

    private final CustomerDao customerDao;

    private String aesKey;

    private final JedisClient jedisClient;

    private final ProductDao productDao;

    private final PromotionDao promotionDao;

    private final ChatLogDao chatlogDao;

    private final NaturalLanguageProcessingService naturalLanguageProcessingService;

    private final HistoryDao historyDao;

    private final FCMService fcmService;

    private final DeviceTokenDao deviceTokenDao;

    private final AccentizerService accentizerService;

    private final VisionService visionService;

    private final LawDao lawDao;

    private final GasDao gasDao;

    private final TrafficJamDao trafficJamDao;

    private final MessageSuggestionDao messageSuggestionDao;

    @Autowired
    public FacebookMessageServiceImpl(Properties properties, FacebookAPIService facebookAPIService, FBChatHelper helper,
                                      CustomerDao customerDao, JedisClient jedisClient,
                                      ProductDao productDao, PromotionDao promotionDao, ChatLogDao chatlogDao,
                                      NaturalLanguageProcessingService naturalLanguageProcessingService,
                                      HistoryDao historyDao, FCMService fcmService, DeviceTokenDao deviceTokenDao, AccentizerService accentizerService, VisionService visionService, LawDao lawDao, GasDao gasDao, TrafficJamDao trafficJamDao, MessageSuggestionDao messageSuggestionDao) {
        this.properties = properties;
        this.facebookAPIService = facebookAPIService;
        this.aesKey = properties.getProperty("aes.key") + KeyConstant.AES_KEY;
        this.helper = helper;
        this.customerDao = customerDao;
        this.jedisClient = jedisClient;
        this.productDao = productDao;
        this.promotionDao = promotionDao;
        this.chatlogDao = chatlogDao;
        this.naturalLanguageProcessingService = naturalLanguageProcessingService;
        this.historyDao = historyDao;
        this.fcmService = fcmService;
        this.deviceTokenDao = deviceTokenDao;
        this.accentizerService = accentizerService;
        this.visionService = visionService;
        this.lawDao = lawDao;
        this.gasDao = gasDao;
        this.trafficJamDao = trafficJamDao;
        this.messageSuggestionDao = messageSuggestionDao;
    }

    /**
     * Handle message
     *
     * @param request
     * @param response
     */
    @Override
    public void handleFacebookMessageFromUser(HttpServletRequest request, HttpServletResponse response) {
        LOG.info("[handleFacebookMessageFromUser] Start");

        try {
            /**
             * store the request body in stringbuilder
             */
            StringBuilder body = new StringBuilder();
            String line;

            BufferedReader reader = request.getReader();
            while ((line = reader.readLine()) != null) {
                body.append(line);
            }

            /**
             * convert the string request body in java object
             */
            FbMsgRequest fbMsgRequest = new Gson().fromJson(body.toString(), FbMsgRequest.class);

            if (fbMsgRequest == null) {
                response.setStatus(HttpServletResponse.SC_OK);
                return;
            }

            List<Messaging> messagings = fbMsgRequest.getEntry().get(0).getMessaging();

            for (Messaging event : messagings) {
                String sender = event.getSender().getId();
                Message message = event.getMessage();

                if (message != null) {
                    if (message.getText() != null) {
                        handleFacebookMessageText(event, sender);
                    } else if (message.getAttachments() != null) {
                        Attachment attachment = message.getAttachments().get(0);
                        if ("image".equals(attachment.getType())) {
                            //handleFacebookMessageImage(attachment.getPayload().getUrl(), sender);
                        }
                    }

                } else if (event.getPostback() != null) {
                    handleFacebookPostback(event, sender);
                }
            }
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (IOException e) {
            LOG.error("[handleFacebookMessageFromUser] IOException : " + e);
        }
        LOG.info("[handleFacebookMessageFromUser] End");
    }

    /**
     * Handle user send text message
     *
     * @param event
     * @param userId
     */
    @Override
    public void handleFacebookMessageText(Messaging event, String userId) {
        LOG.info("[handleFacebookMessageText] Start: userId = " + userId);
        String text = event.getMessage().getText();
        jedisClient.set(userId + "_Say", "", 3600);
        if (text.startsWith(Constant.SHORT_KEY_HISTORY_INPUT)) {
            String[] parts = text.split(":");
            String time = parts[Constant.TEXT_INPUT_HISTORY];
        } else if (text.startsWith(Constant.SHORT_KEY.TYPE.getValue())) {
            // short key
            handleShortKey(text, userId);
        } else if (text.startsWith(Constant.PHONE_PREFIX)) {

            // phone
            savePhoneNumber(userId, text.substring(1));
        } else {

            // normal text
            /*String responseText = naturalLanguageProcessingService.processSpeech(accentizerService.add(text));*/
            //String responseText = naturalLanguageProcessingService.processSpeech2(text);
            text=text.replace("ko","không").replace("hong", "không");
            Map<String, Object> data = naturalLanguageProcessingService.processSpeech2(text);
            boolean result=false;
            if ((Integer) data.get("status") == 200 &&!data.get("speech").equals("")) {
                if (Constant.GREETING.equals(data.get("intent"))) {
                    sendWelcomeMessage(userId);
                    result=true;
                }
                else if (Constant.INTENT_SEARCH_FAULT.equals(data.get("intent"))) {
                    jedisClient.set(userId + "_Say", text, 3600);
                    result=getLawsMessage(userId, (String) data.get("speech"), String.valueOf(Constant.BEGIN_SHOW));
                }
                else if (Constant.INTENT_SEARCH_TRACFFICJAM.equals(data.get("intent"))) {
                    jedisClient.set(userId + "_Say", text, 3600);
                    getTrafficJam(userId, (String) data.get("speech"));
                    result=true;
                }
                else if (Constant.INTENT_SEARCH_GAS.equals(data.get("intent"))) {
                    jedisClient.set(userId + "_Say", text, 3600);
                    getGasToday(userId, (String) data.get("speech"));
                    result=true;
                }
                else if (Constant.INTENT_ENCOURAGE_SPEECH.equals(data.get("intent"))) {
                    jedisClient.set(userId + "_Say", text, 3600);
                    answerNormalSpeech(userId, (String) data.get("speech"));
                    result=true;
                }
                else if (Constant.INTENT_NORMAL_SPEECH.equals(data.get("intent"))) {
                    jedisClient.set(userId + "_Say", text, 3600);
                    answerNormalSpeech(userId, (String) data.get("speech"));
                    result=true;
                }
                else if (Constant.INTENT_SEARCH_FAULT_COMBINE.equals(data.get("intent"))) {
                    jedisClient.set(userId + "_Say", text, 3600);
                    result=getLawsMessage(userId, (String) data.get("speech"), String.valueOf(Constant.BEGIN_SHOW));
                }else{
                    result=getLawsMessage(userId, text, String.valueOf(Constant.BEGIN_SHOW));
                }
                if(!result){
                    String textNotUnderstand="Emily do not understand";
                    Map<String, Object> data2 =naturalLanguageProcessingService.processSpeech2(textNotUnderstand);
                    facebookAPIService.sendTextMessage(userId, (String) data2.get("speech"));
                }
            }else{
                    String textNotUnderstand="Emily do not understand";
                    Map<String, Object> data2 =naturalLanguageProcessingService.processSpeech2(textNotUnderstand);
                    facebookAPIService.sendTextMessage(userId, (String) data2.get("speech"));
            }
//            if (Constant.GREETING.equals(data.get("intent"))) {
//                sendWelcomeMessage(userId);
//            } else if (!responseText.equals(Constant.DO_NOT_UNDERSTAND_MESSAGE)) {
//                jedisClient.set(userId + "_Say", text, 3600);
//                getLawsMessage(userId, responseText, String.valueOf(Constant.BEGIN_SHOW));
//           }
// else {
//                responseText = naturalLanguageProcessingService.processSpeech(accentizerService.add(text).toLowerCase());
//                jedisClient.set(userId + "_Say", text, 3600);
//                getLawsMessage(userId, responseText, String.valueOf(Constant.BEGIN_SHOW));
//            }
        }

        LOG.info("[handleFacebookMessageText] End");
    }

//    @Override
//    public void handleFacebookMessageImage(String url, String userId) {
//        LOG.info("[handleFacebookMessageImage] Start: url = " + url);
//        InputStream is = null;
//        try {
//            URL u = new URL(url);
//            is = u.openStream();
//            byte[] imageBytes = IOUtils.toByteArray(is);
//            List<EntityAnnotation> logos = visionService.detectLogo(imageBytes, 1);
//            if (!logos.isEmpty()) {
//                jedisClient.set(userId + "_Say", logos.get(0).getDescription(), 3600);
//                //getProductsMessage(userId, logos.get(0).getDescription(), "0");
//            } else {
//                facebookAPIService.sendTextMessage(userId, "\u0058\u0069\u006e \u006c\u1ed7\u0069 \u0062\u1ea1\u006e\u002c \u0063\u0068\u00fa\u006e\u0067 \u0074\u00f4\u0069 \u006b\u0068\u00f4\u006e\u0067 \u0074\u0068\u1ec3 \u006e\u0068\u1ead\u006e \u0064\u0069\u1ec7\u006e \u0073\u1ea3\u006e \u0070\u0068\u1ea9\u006d \u0074\u0072\u006f\u006e\u0067 \u1ea3\u006e\u0068\u002c \u0062\u1ea1\u006e \u0063\u00f3 \u0074\u0068\u1ec3 \u0074\u0068\u1eed \u006e\u0068\u1ead\u0070 \u0063\u0068\u1eef\u002e");
//            }
//            LOG.info("[handleFacebookMessageImage] End");
//        } catch (MalformedURLException e) {
//            LOG.info("[handleFacebookMessageImage] MalformedURLException: " + e);
//        } catch (IOException e) {
//            LOG.info("[handleFacebookMessageImage] IOException: " + e);
//        } finally {
//            if (is != null) {
//                try {
//                    is.close();
//                } catch (IOException e) {
//                    LOG.info("[handleFacebookMessageImage] IOException: " + e);
//                }
//            }
//        }
//    }

    /**
     * Handle short key
     *
     * @param text
     * @param userId
     */
    private void handleShortKey(String text, String userId) {
        LOG.info("[handleShortKey] Start: text = " + text);
        if (Constant.SHORT_KEY.FEEDBACK.getValue().equals(text)) {
            handleFacebookMenuPostbackFeedback(userId);
        } else {
            facebookAPIService.sendTextMessage(userId, Constant.SHORT_KEY_NOT_FOUND);
        }
        LOG.info("[handleShortKey] End");
    }

    /**
     * Save user phone number
     *
     * @param userId
     * @param phone
     */
    private void savePhoneNumber(String userId, String phone) {
        LOG.info("[savePhoneNumber] Start");
        Customer customer = customerDao.getByPhone(phone);

        if (customer != null) {
            if ("".equals(customer.getBotFbId()) && !userId.equals(customer.getBotFbId())) {
                customer.setBotFbId(userId);
                customerDao.update(customer);
                deviceTokenDao.insertBotFbIdByAppFbId(userId, customer.getAppFbId());
                facebookAPIService.sendTextMessage(userId, Constant.SAVED_PHONE);
            } else if (customer.getBotFbId().equals(userId)) {
                facebookAPIService.sendTextMessage(userId, Constant.PHONE_ALREADY_REGISTERED_BY_THAT_USER);
            } else {
                facebookAPIService.sendTextMessage(userId, Constant.PHONE_ALREADY_REGISTERED);
            }
        } else {
            customer = customerDao.getByBotFBId(userId);
            if (customer == null) {
                FbProfile profile = getProfile(userId);

                customer = new Customer();
                customer.setBotFbId(userId);
                customer.setPhone(phone);
                customer.setFirstName(profile.getFirstName());
                customer.setLastName(profile.getLastName());
                customer.setAppFbId("");
                customerDao.insert(customer);
            } else {
                customerDao.insertPhoneByBotFbId(userId, phone);
            }

            facebookAPIService.sendTextMessage(userId, Constant.SAVED_PHONE);
        }

        LOG.info("[savePhoneNumber] End");
    }

    /**
     * Send welcome message to user
     *
     * @param userId
     */
    private void sendWelcomeMessage(String userId) {
        LOG.info("[sendWelcomeMessage] Start");

        FbProfile profile = getProfile(userId);

        Customer customer = customerDao.getByBotFBId(userId);

        // insert new account
        if (customer == null) {
            customer = customerDao.insert(
                    new Customer(userId, "", "", profile.getFirstName(), profile.getLastName()));
        }

        StringBuilder msg = new StringBuilder(Constant.HELLO_MESSAGE).append(" ")
                .append(profile.getFirstName()).append(" ").append(profile.getLastName()).append(", ")
                .append(Constant.WELCOME_MESSAGE).append("\n");

        facebookAPIService.sendTextMessage(userId, String.valueOf(msg));
        //facebookAPIService.sendTextMessage(userId, Constant.INSTALL_APP_MESSAGE);

        LOG.info("[sendWelcomeMessage] End");
    }

    /**
     * Get user's profile
     *
     * @param userId
     * @return
     */
    private FbProfile getProfile(String userId) {
        LOG.info("[getProfile] Start");
        String link = StringUtils.replace(properties.getProperty("profile_url")
                        + AESUtil.decryptByAES(properties.getProperty(Constant.PAGE_ACCESS_TOKEN), aesKey)
                , "SENDER_ID", userId);
        LOG.info("[getProfile] End");
        return FBChatHelper.getObjectFromUrl(link, FbProfile.class);
    }

    /**
     * Handle user tap button
     *
     * @param event
     * @param userId
     */
    @Override
    public void handleFacebookPostback(Messaging event, String userId) {
        LOG.info("[handleFacebookPostback] Start: userId = " + userId);

        if (Constant.GET_START.equals(event.getPostback().getPayload())) {
            sendWelcomeMessage(userId);
        } else {
            Map<String, String> map = new HashMap<>();
            map = new Gson().fromJson(event.getPostback().getPayload(), map.getClass());

            String type = map.get("type");

            if (Constant.POST_BACK_TYPE.TYPE_BUTTON.getValue().equals(type)) {
                handleMessageButton(map.get("typeButton"), userId, map);
            } else if (Constant.POST_BACK_TYPE.TYPE_MENU.getValue().equals(type)) {
                handleMenuButton(map.get("typeMenu"), userId);
            }
        }

        LOG.info("[handleFacebookPostback] End");
    }

    /**
     * Handle button on message
     *
     * @param typeButton
     * @param userId
     * @param map
     */
    private void handleMessageButton(String typeButton, String userId, Map<String, String> map) {
        LOG.info("[handleMessageButton] Start: typeButton = " + typeButton);
        List<SaveData> list = (List<SaveData>) jedisClient.get(userId + Constant.CART_POST_FIX);
        List<Long> listId = null;
        if (list != null) {
            listId = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                listId.add(list.get(i).getProductId());
            }
        }
        if (Constant.BUTTON_TYPE.DETAIL.getValue().equals(typeButton)) {
            handleFacebookPostbackDetail(userId, map.get("text"));
        }  else if (Constant.BUTTON_TYPE.OPTIONAL_SHOW.getValue().equals(typeButton)) {
            handleOptionalButton(userId, listId, map);
        }
        LOG.info("[handleMessageButton] End");
    }

    /**
     * Handle optional button
     *
     * @param userId
     * @param listId
     * @param map
     */
    private void handleOptionalButton(String userId, List<Long> listId, Map<String, String> map) {
        LOG.info("[handleOptionalButton] Start");
       if (Constant.TYPE_SHOW_SEARCH.equals(map.get("typeShow"))) {
            //getProductsMessage(userId, map.get("nameProduct"), map.get("positionShow"));
            getLawsMessage(userId, map.get("nameProduct"), map.get("positionShow"));
        }
        LOG.info("[handleOptionalButton] End");
    }

    private void handleMenuButton(String typeMenu, String userId) {
        LOG.info("[handleMenuButton] Start: typeMenu = " + typeMenu);

        if (Constant.MENU_TYPE.LAWTODAY.getValue().equals(typeMenu)) {
            handleFacebookMenuPostbackLawToday(userId);
        } else if (Constant.MENU_TYPE.SMARTTRAFFIC.getValue().equals(typeMenu)) {
            handleFacebookMenuPostbackSmartTraffic(userId);
        } else if (Constant.MENU_TYPE.FEEDBACK.getValue().equals(typeMenu)) {
            handleFacebookMenuPostbackFeedback(userId);
        }
        LOG.info("[handleMenuButton] End");
    }

    /**
     * Handle feedback cmd
     *
     * @param userId
     */
    private void handleFacebookMenuPostbackFeedback(String userId) {
        LOG.info("[handleFacebookMenuPostbackFeedback] Start: userId = " + userId);
        String textUserSay = (String) jedisClient.get(userId + "_Say");
        String text;
        if (textUserSay == null) {
            text = "\u0042\u1ea1\u006e \u0111\u00e3 \u0070\u0068\u1ea3\u006e \u0068\u1ed3\u0069 \u0074\u0068\u00e0\u006e\u0068 \u0063\u00f4\u006e\u0067";
        } else if (textUserSay.equals("")) {
            //Cám ơn bạn đã phản hồi!
            text = "\u0043\u00e1\u006d \u01a1\u006e \u0062\u1ea1\u006e \u0111\u00e3 \u0070\u0068\u1ea3\u006e \u0068\u1ed3\u0069\u0021";
        } else {
            ChatLog chatLogEntity = new ChatLog();
            chatLogEntity.setUserSay(textUserSay);
            chatLogEntity.setStatus(Constant.STATUS_NEW);
            chatlogDao.insert(chatLogEntity);
            //Cám ơn bạn đã phản hồi!
            text = "\u0043\u00e1\u006d \u01a1\u006e \u0062\u1ea1\u006e \u0111\u00e3 \u0070\u0068\u1ea3\u006e \u0068\u1ed3\u0069\u0021";
            jedisClient.remove(userId + "_Say");
        }
        facebookAPIService.sendTextMessage(userId, text);

        LOG.info("[handleFacebookMenuPostbackFeedback] End");
    }

    /**
     * Handle show product details cmd
     *
     * @param userId
     * @param productId
     */



    /**
     * Generate reply
     *
     * @param userId
     * @param elementList
     * @return
     */
    private String generateReply(String userId, List<Element> elementList) {
        LOG.info("[generateReply] Start");

        Payload payload = new Payload();
        payload.setElements(elementList);
        payload.setTemplateType("generic");

        Attachment attachment = new Attachment();
        attachment.setPayload(payload);
        attachment.setType("template");

        Message message = new Message();
        message.setAttachment(attachment);

        Recipient recipient = new Recipient();
        recipient.setId(userId);

        Messaging reply = new Messaging();
        reply.setRecipient(recipient);
        reply.setMessage(message);

        LOG.info("[generateReply] End");
        return new Gson().toJson(reply);
    }

    private void getProductsHistory(String userId) {
        LOG.info("[getProductsHistory] Start: userId = " + userId);
        List<Element> elementList = new ArrayList<>();

        List<String> listDate = historyDao.getDateHistory(userId);
        if (!listDate.isEmpty()) {
            for (String date : listDate) {
                Element element = new Element();

                element.setTitle(date);
                long millisTime = DateUtil.parseMillisecondFromString(date, "dd/MM/yyyy");
                int sumProduct = historyDao.getQuantityProductByTime(userId, millisTime);
                StringBuilder strSubtitle = new StringBuilder("\u0042\u1ea1\u006e \u0111\u00e3 \u006d\u0075\u0061\u003a ")
                        .append(sumProduct)
                        .append(" \u006d\u00f3\u006e \u0068\u00e0\u006e\u0067");

                element.setSubtitle(String.valueOf(strSubtitle));
                String urlPicture;
                switch (sumProduct) {
                    case 1:
                        urlPicture = "https://tinyurl.com/zm73o3e";
                        break;
                    case 2:
                        urlPicture = "https://tinyurl.com/jgh39ha";
                        break;
                    default:
                        urlPicture = "https://tinyurl.com/z8ycfod";
                        break;
                }


                element.setImageUrl(urlPicture);
                List<Button> buttonList = new ArrayList<>();
                buttonList.add(helper.createShowDetailHistoryButton(date));
                buttonList.add(helper.createAddHistoryToCartButton(date));
                element.setButtons(buttonList);

                elementList.add(element);
            }
            LOG.info("[getProductsMessage] End");
            facebookAPIService.sendTextMessage(generateReply(userId, elementList));
        } else {
            //Hiện bạn là thành viên mới, tìm mua thành công món hàng quay lại xem lịch sử sau nhé!
            String text = "\u0048\u0069\u1ec7\u006e \u0062\u1ea1\u006e \u006c\u00e0 \u0074\u0068\u00e0\u006e\u0068 \u0076\u0069\u00ea\u006e \u006d\u1edb\u0069\u002c \u0074\u00ec\u006d \u006d\u0075\u0061 \u0074\u0068\u00e0\u006e\u0068 \u0063\u00f4\u006e\u0067 \u006d\u00f3\u006e \u0068\u00e0\u006e\u0067 \u0071\u0075\u0061\u0079 \u006c\u1ea1\u0069 \u0078\u0065\u006d \u006c\u1ecb\u0063\u0068 \u0073\u1eed \u0073\u0061\u0075 \u006e\u0068\u00e9\u0021";
            LOG.info("[getProductsHistory] End");
            facebookAPIService.sendTextMessage(userId, text);
        }
    }

    private void handleSendMessageTextAndButton(String userId, List<Button> buttonList, String optionalMessage) {
        LOG.info("[handleSendMessageTextAndButton] Start: userId = " + userId);
        Payload payload = new Payload();
        //kieu text va nut
        payload.setTemplateType("button");
        payload.setText(optionalMessage);
        payload.setButtons(buttonList);
        Attachment attachment = new Attachment();
        attachment.setPayload(payload);
        attachment.setType("template");

        Message message = new Message();
        message.setAttachment(attachment);

        Recipient recipient = new Recipient();
        recipient.setId(userId);
        Messaging reply = new Messaging();
        reply.setRecipient(recipient);
        reply.setMessage(message);
        facebookAPIService.sendTextMessage(new Gson().toJson(reply));
        LOG.info("[handleSendMessageTextAndButton] End");
    }

    private String getMessageAfterCheck(boolean nextShow, boolean backShow) {
        LOG.info("[getMessageAfterCheck] Start:");
        String optionalMessage = Constant.STR_BLANK;
        if (nextShow && backShow) {
            //Bạn có muốn quay lại trước hay xem tiếp món hàng :D
            optionalMessage = "\u0042\u1ea1\u006e \u0063\u00f3 \u006d\u0075\u1ed1\u006e \u0074\u0069\u1ebf\u0070 \u0074\u1ee5\u0063 \u0078\u0065\u006d \u0063\u00e1\u0063 \u0063\u00e1\u0063 \u006d\u00f3\u006e \u0068\u00e0\u006e\u0067 \u006b\u0068\u00f4\u006e\u0067 \u003a\u0044";
        } else {
            if (nextShow) {
                //"Bạn muốn xem các trường hợp khác không <(") <(")"
                optionalMessage = "\u0042\u1ea1\u006e \u006d\u0075\u1ed1\u006e \u0078\u0065\u006d \u0063\u00e1\u0063 \u0074\u0072\u01b0\u1edd\u006e\u0067 \u0068\u1ee3\u0070 \u006b\u0068\u00e1\u0063 \u006b\u0068\u00f4\u006e\u0067 \u003c\u0028\u005c\u0022\u0029 \u003c\u0028\u005c\u0022\u0029";
            } else if (backShow) {
                //Bạn có muốn xem lại các món hàng trước không :D
                optionalMessage = "\u0042\u1ea1\u006e \u006d\u0075\u1ed1\u006e \u0078\u0065\u006d \u006c\u1ea1\u0069 \u0063\u00e1\u0063 \u0074\u0072\u01b0\u1edd\u006e\u0067 \u0068\u1ee3\u0070 \u0074\u0072\u01b0\u1edb\u0063 \u006b\u0068\u00f4\u006e\u0067\u003f \u003c\u0028\u005c\u0022\u0029 \u003c\u0028\u005c\u0022\u0029";
            }
        }
        LOG.info("[getMessageAfterCheck] End");
        return optionalMessage;
    }



    private void sendDataToDeviceByBotFbId(String userId, List<SaveData> listProductId, int statusCode) {
        LOG.info("[sendDataToDeviceByBotFbId] Start: userId " + userId);
        ProductResponse productResponse = new ProductResponse(listProductId, statusCode);
        Gson gson = new Gson();
        String jSonproductId = gson.toJson(productResponse);
        List<String> listToken = deviceTokenDao.getDeviceTokenByBotFbId(userId);
        for (String token : listToken) {
            fcmService.sendDataMessage(jSonproductId, token);
        }
        LOG.info("[sendDataToDeviceByBotFbId] End");
    }

    //Law handle
    private boolean getLawsMessage(String userId, String name, String positionInResult) {
        boolean result=true;
        LOG.info("[getLawsMessage] Start: name = " + name);
        List<Element> elementList = new ArrayList<>();
        //bien kiem tra
        boolean nextShow = false;
        boolean backShow = false;
        int intPositionInResult = Integer.parseInt(positionInResult);
        List<Law> lawList = lawDao.getLawBySearchName(name, intPositionInResult, Constant.MAX_SHOW_RESULT + Constant.CHECK_NEXT_SEARCH_RESULT);
        List<Law> listRight = new ArrayList<>();
        List<Law> listRightLike = new ArrayList<>();
        for (Law alaw : lawList) {
            if (alaw.getName().contains(name) && alaw.getName().indexOf(name) == 0) {
                listRight.add(alaw);
            } else {
                listRightLike.add(alaw);
            }
        }
        lawList.clear();
        lawList.addAll(listRight);
        lawList.addAll(listRightLike);
        int sizeListShowResult = 0;
        if (lawList.size() - Constant.MAX_SHOW_RESULT == Constant.CHECK_NEXT_SEARCH_RESULT) {
            nextShow = true;
            sizeListShowResult = Constant.MAX_SHOW_RESULT;
        } else if (lawList.size() - Constant.MAX_SHOW_RESULT < Constant.CHECK_NEXT_SEARCH_RESULT) {
            sizeListShowResult = lawList.size();
        }

        if (!lawList.isEmpty()) {

            for (int i = 0; i < sizeListShowResult; i++) {
                Law law = lawList.get(i);
                Element element = new Element();
                element.setTitle(law.getName());
                if (law.getVehicleId() == 1) {
                    element.setImageUrl("https://www.shareicon.net/data/256x256/2016/08/18/815021_transportation_512x512.png");
                } else {
                    element.setImageUrl("https://freeiconshop.com/wp-content/uploads/edd/car-flat.png");
                }

                String strSubtitle = law.getMoney();
                element.setSubtitle(strSubtitle);
                List<Button> buttonList = new ArrayList<>();
                buttonList.add(helper.createDetailButton(String.valueOf(law.getId())));
                //buttonList.add(helper.createAddButton(String.valueOf(law.getId())));
                element.setButtons(buttonList);
                elementList.add(element);
            }
            facebookAPIService.sendTextMessage(generateReply(userId, elementList));

            List<Button> buttonList = new ArrayList<>();

            if (nextShow) {
                buttonList.add(helper.createMoreShowButton(String.valueOf(intPositionInResult + Constant.MAX_SHOW_RESULT),
                        Constant.TYPE_SHOW_SEARCH, name));
            }
            if (intPositionInResult > 0) {
                buttonList.add(helper.createBackShowButton(String.valueOf(intPositionInResult - Constant.MAX_SHOW_RESULT),
                        Constant.TYPE_SHOW_SEARCH, name));
                backShow = true;
            }
            String optionalMessage = getMessageAfterCheck(nextShow, backShow);
            if (nextShow || backShow) {
                handleSendMessageTextAndButton(userId, buttonList, optionalMessage);
            }

            //Gợi ý hỏi xem có bị công an bắt không
//            List<Button> buttonList2 = new ArrayList<>();
//            String messageSuggest="bạn bị công an bắt hả?";
//            buttonList2.add(helper.createAnswerYesButton(
//                    Constant.TYPE_ANSWER, name));
//            buttonList2.add(helper.createAnswerNoButton(
//                    Constant.TYPE_ANSWER, name));
//            handleSendMessageTextAndButton(userId, buttonList2, messageSuggest);

        } else {
            String textNotUnderstand="Emily do not understand";
            Map<String, Object> data2 =naturalLanguageProcessingService.processSpeech2(textNotUnderstand);
            facebookAPIService.sendTextMessage(userId, (String) data2.get("speech"));
        }
        LOG.info("[getLawsMessage] End");
        return result;
    }

    private void handleFacebookPostbackDetail(String userId, String lawId) {
        LOG.info("[handleFacebookPostbackDetail] Start: userId = " + userId);
        StringBuilder text = new StringBuilder();
        Law law = lawDao.getLawById(Long.parseLong(lawId));
        //Chi tiết:
        text.append("\u0043\u0068\u0069 \u0074\u0069\u1ebf\u0074\u003a")
                .append("\n")
                .append(law.getDetail());
        facebookAPIService.sendTextMessage(userId, String.valueOf(text));
        String textSuggest = messageSuggestionDao.getMessageByVehicleId(law.getVehicleId()).get(0).getDetail();
        facebookAPIService.sendTextMessage(userId, textSuggest);
        LOG.info("[handleFacebookPostbackDetail] End");
    }

    //Law handle
    private void getGasToday(String userId, String kindGasName) {
        LOG.info("[getGasToday] Start: userId = " + userId);
        List<Gas> listGas = gasDao.getByName(kindGasName);
        String text = "Giá xăng dầu hôm nay nè: \n";
        if (!listGas.isEmpty()) {
            for (Gas gas : listGas) {
                text = text + gas.getName() + ": " + gas.getPrice() + " đồng/" + gas.getUnit() + "\n";
            }
        } else {
            text = "Giá xăng dầu đang thay đổi, Emily sẽ cập nhật ngay, đợi tí nhé!";
        }
        facebookAPIService.sendTextMessage(userId, text);
        LOG.info("[getGasToday] End");
    }

    private void getTrafficJam(String userId, String nameLocation) {
        LOG.info("[getGasToday] Start: userId = " + userId);
        List<TrafficJam> listTrafficJam = trafficJamDao.getTrafficJamByName(nameLocation);
        String text = "";
        if (!listTrafficJam.isEmpty()) {
            for (TrafficJam trafficJam : listTrafficJam) {
                text = text + trafficJam.getName() + ":\n" + trafficJam.getDetailJam() + "\n";
            }
        } else {
            text = " Emily nghĩ khúc này không kẹt, đi thoải mái nhé!";
        }
        facebookAPIService.sendTextMessage(userId, text);
        LOG.info("[getGasToday] End");
    }


    private void answerNormalSpeech(String userId, String speechAnswer) {
        LOG.info("[answerNormalSpeech] Start: userId = " + userId);
        facebookAPIService.sendTextMessage(userId, speechAnswer);
        LOG.info("[answerNormalSpeech] End");
    }

    //Menu handle
    private void handleFacebookMenuPostbackLawToday(String userId) {
        LOG.info("[handleFacebookMenuPostbackLawToday] Start: userId = " + userId);
        String speechAnswer = "Hiện tại chức năng đang xây dựng, Emily rất xin lỗi vì sự bất tiện này!";
        facebookAPIService.sendTextMessage(userId, speechAnswer);
        LOG.info("[handleFacebookMenuPostbackLawToday] End");
    }
    //Menu handle
    private void handleFacebookMenuPostbackSmartTraffic(String userId) {
        LOG.info("[handleFacebookMenuPostbackLawToday] Start: userId = " + userId);
        String speechAnswer = "Hiện tại chức năng đang xây dựng, Emily rất xin lỗi vì sự bất tiện này!";
        facebookAPIService.sendTextMessage(userId, speechAnswer);
        LOG.info("[handleFacebookMenuPostbackLawToday] End");
    }
}

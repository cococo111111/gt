package com.fu.bot.utils;

import com.fu.bot.model.Button;
import com.fu.bot.model.Payload;
import com.google.gson.Gson;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by manlm on 9/3/2016.
 */
@Component
public class FBChatHelper {

    private static final Logger LOG = Logger.getLogger(FBChatHelper.class);

    private FBChatHelper() {
        // default constructor
    }

    /**
     * Returns object of type clazz from an json api link
     *
     * @param link
     * @param clazz
     * @return
     * @throws Exception
     */
    public static <T> T getObjectFromUrl(String link, Class<T> clazz) {
        LOG.info("[getObjectFromUrl]");

        T t = null;
        URL url;
        StringBuilder jsonString = new StringBuilder();
        try {
            url = new URL(link);
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    url.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                jsonString.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            LOG.error("[getObjectFromUrl] IOException: " + e);
        }
        if (!StringUtils.isEmpty(jsonString)) {
            Gson gson = new Gson();
            t = gson.fromJson(String.valueOf(jsonString), clazz);
        }

        LOG.info("[getObjectFromUrl] End");
        return t;
    }

    public Button createDetailButton(String id) {
        LOG.info("[createDetailButton] Start");
        Payload payload = new Payload();
        payload.setType(Constant.POST_BACK_TYPE.TYPE_BUTTON.getValue());
        payload.setTypeButton("detail");
        payload.setText(id);
        LOG.info("[createDetailButton] End");
        return new Button(Constant.POSTBACK, Constant.BASIC_BUTTON.DETAIL_PRODUCT.getValue()
                , new Gson().toJson(payload));
    }

    public Button createAddButton(String id) {
        LOG.info("[createAddButton] Start");
        Payload payload = new Payload();
        payload.setType(Constant.POST_BACK_TYPE.TYPE_BUTTON.getValue());
        payload.setTypeButton(Constant.BUTTON_TYPE.ADD.getValue());
        payload.setText(id);

        LOG.info("[createAddButton] End");
        return new Button(Constant.POSTBACK, Constant.BASIC_BUTTON.ADD_PRODUCT.getValue()
                , new Gson().toJson(payload));
    }

    public Button createRemoveButton(String id) {
        LOG.info("[createRemoveButton] Start");
        Payload payload = new Payload();
        payload.setType(Constant.POST_BACK_TYPE.TYPE_BUTTON.getValue());
        payload.setTypeButton(Constant.BUTTON_TYPE.REMOVE.getValue());
        payload.setText(id);

        LOG.info("[createRemoveButton] End");
        return new Button(Constant.POSTBACK, Constant.BASIC_BUTTON.REMOVE_PRODUCT.getValue()
                , new Gson().toJson(payload));
    }

    public Button createSuggestButton(String id) {
        LOG.info("[createRemoveButton] Start");
        Payload payload = new Payload();
        payload.setType(Constant.POST_BACK_TYPE.TYPE_BUTTON.getValue());
        payload.setTypeButton(Constant.BUTTON_TYPE.SUGGEST.getValue());
        payload.setText(id);

        LOG.info("[createRemoveButton] End");
        return new Button(Constant.POSTBACK, Constant.BASIC_BUTTON.SUGGEST.getValue()
                , new Gson().toJson(payload));
    }

    public Button createMoreHistoryButton(String timeHistory,String positionHistory) {
        LOG.info("[MoreHistoryButton] Start");
        Payload payload = new Payload();
        payload.setType(Constant.POST_BACK_TYPE.TYPE_BUTTON.getValue());
        payload.setTypeButton(Constant.BUTTON_TYPE.OPTIONAL_HISTORY.getValue());
        payload.setPositionHistory(positionHistory);
        payload.setTimeHistory(timeHistory);
        LOG.info("[MoreHistoryButton] End");
        return new Button(Constant.POSTBACK, Constant.BASIC_BUTTON.SEE_MORE_HISTORY.getValue()
                , new Gson().toJson(payload));
    }

    public Button createBackHistoryButton(String timeHistory,String positionHistory) {
        LOG.info("[createBackHistoryButton] Start");
        Payload payload = new Payload();
        payload.setType(Constant.POST_BACK_TYPE.TYPE_BUTTON.getValue());
        payload.setTypeButton(Constant.BUTTON_TYPE.OPTIONAL_HISTORY.getValue());
        payload.setPositionHistory(positionHistory);
        payload.setTimeHistory(timeHistory);
        LOG.info("[createBackHistoryButton] End");
        return new Button(Constant.POSTBACK, Constant.BASIC_BUTTON.SEE_BACK_HISTORY.getValue()
                , new Gson().toJson(payload));
    }

    public Button createShowDetailHistoryButton(String timeHistory) {
        LOG.info("[createShowDetailHistoryButton] Start");
        Payload payload = new Payload();
        payload.setType(Constant.POST_BACK_TYPE.TYPE_BUTTON.getValue());
        payload.setTypeButton(Constant.BUTTON_TYPE.OPTIONAL_HISTORY.getValue());
        payload.setPositionHistory(String.valueOf(Constant.BEGIN_HISTORY));
        payload.setTimeHistory(timeHistory);
        LOG.info("[createShowDetailHistoryButton] End");
        return new Button(Constant.POSTBACK, Constant.BASIC_BUTTON.SEE_DETAIL_HISTORY.getValue()
                , new Gson().toJson(payload));
    }

    public Button createAddHistoryToCartButton(String timeHistory) {
        LOG.info("[createAddHistoryToCartButton] Start");
        Payload payload = new Payload();
        payload.setType(Constant.POST_BACK_TYPE.TYPE_BUTTON.getValue());
        payload.setTypeButton(Constant.BUTTON_TYPE.TRACKING_HISTORY.getValue());
        payload.setTimeHistory(timeHistory);
        LOG.info("[createAddHistoryToCartButton] End");
        return new Button(Constant.POSTBACK, Constant.BASIC_BUTTON.ADD_TRACKING_HISTORY.getValue()
                , new Gson().toJson(payload));
    }

    public Button createLawTodayMenu() {
        LOG.info("[createFeedBackButton] Start");
        Payload payload = new Payload();
        payload.setType(Constant.POST_BACK_TYPE.TYPE_MENU.getValue());
        payload.setTypeMenu(Constant.MENU_TYPE.LAWTODAY.getValue());
        LOG.info("[createFeedBackButton] End");
        return new Button(Constant.POSTBACK, Constant.BASIC_BUTTON.LAWTODAY.getValue()
                , new Gson().toJson(payload));
    }
    public Button createSmartTrafficMenu() {
        LOG.info("[createFeedBackButton] Start");
        Payload payload = new Payload();
        payload.setType(Constant.POST_BACK_TYPE.TYPE_MENU.getValue());
        payload.setTypeMenu(Constant.MENU_TYPE.SMARTTRAFFIC.getValue());
        LOG.info("[createFeedBackButton] End");
        return new Button(Constant.POSTBACK, Constant.BASIC_BUTTON.SMARTTRAFFIC.getValue()
                , new Gson().toJson(payload));
    }

    public Button createFeedBackMenu() {
        LOG.info("[createFeedBackButton] Start");
        Payload payload = new Payload();
        payload.setType(Constant.POST_BACK_TYPE.TYPE_MENU.getValue());
        payload.setTypeMenu(Constant.MENU_TYPE.FEEDBACK.getValue());
        LOG.info("[createFeedBackButton] End");
        return new Button(Constant.POSTBACK, Constant.BASIC_BUTTON.FEEDBACK.getValue()
                , new Gson().toJson(payload));
    }

    public Button createMoreShowButton(String positionNext, String typeShow, String nameProduct) {
        LOG.info("[createMoreCartButton] Start");
        Payload payload = new Payload();
        payload.setType(Constant.POST_BACK_TYPE.TYPE_BUTTON.getValue());
        payload.setTypeButton(Constant.BUTTON_TYPE.OPTIONAL_SHOW.getValue());
        payload.setPositionShow(positionNext);
        payload.setTypeShow(typeShow);
        payload.setNameProduct(nameProduct);
        LOG.info("[createMoreCartButton] End");
        return new Button(Constant.POSTBACK, Constant.BASIC_BUTTON.NEXT_SHOW.getValue()
                , new Gson().toJson(payload));
    }

    public Button createBackShowButton(String positionBack,String typeShow, String nameProduct) {
        LOG.info("[createBackShowButton] Start");
        Payload payload = new Payload();
        payload.setType(Constant.POST_BACK_TYPE.TYPE_BUTTON.getValue());
        payload.setTypeButton(Constant.BUTTON_TYPE.OPTIONAL_SHOW.getValue());
        payload.setPositionShow(positionBack);
        payload.setTypeShow(typeShow);
        payload.setNameProduct(nameProduct);
        LOG.info("[createBackShowButton] End");
        return new Button(Constant.POSTBACK, Constant.BASIC_BUTTON.BACK_SHOW.getValue()
                , new Gson().toJson(payload));
    }

    public Button createMoreShowPromotionButton(String positionNext, String typeShow, String userId) {
        LOG.info("[createMoreCartButton] Start");
        Payload payload = new Payload();
        payload.setType(Constant.POST_BACK_TYPE.TYPE_BUTTON.getValue());
        payload.setTypeButton(Constant.BUTTON_TYPE.OPTIONAL_SHOW.getValue());
        payload.setPositionShow(positionNext);
        payload.setTypeShow(typeShow);
        payload.setUserId(userId);
        LOG.info("[createMoreCartButton] End");
        return new Button(Constant.POSTBACK, Constant.BASIC_BUTTON.NEXT_SHOW.getValue()
                , new Gson().toJson(payload));
    }

    public Button createBackShowPromotionButton(String positionBack,String typeShow, String userId) {
        LOG.info("[createBackShowButton] Start");
        Payload payload = new Payload();
        payload.setType(Constant.POST_BACK_TYPE.TYPE_BUTTON.getValue());
        payload.setTypeButton(Constant.BUTTON_TYPE.OPTIONAL_SHOW.getValue());
        payload.setPositionShow(positionBack);
        payload.setTypeShow(typeShow);
        payload.setUserId(userId);
        LOG.info("[createBackShowButton] End");
        return new Button(Constant.POSTBACK, Constant.BASIC_BUTTON.BACK_SHOW.getValue()
                , new Gson().toJson(payload));
    }

    public Button createAnswerYesButton(String typeShow, String userId) {
        LOG.info("[createAnswerYesButton] Start");
        Payload payload = new Payload();
        payload.setType(Constant.POST_BACK_TYPE.TYPE_BUTTON.getValue());
        payload.setTypeButton(Constant.BUTTON_TYPE.ANSWER_YES.getValue());
        payload.setTypeShow(typeShow);
        payload.setUserId(userId);
        LOG.info("[createAnswerYesButton] End");
        return new Button(Constant.POSTBACK, Constant.BASIC_BUTTON.ANSWER_YES.getValue()
                , new Gson().toJson(payload));
    }
    public Button createAnswerNoButton(String typeShow, String userId) {
        LOG.info("[createAnswerNoButton] Start");
        Payload payload = new Payload();
        payload.setType(Constant.POST_BACK_TYPE.TYPE_BUTTON.getValue());
        payload.setTypeButton(Constant.BUTTON_TYPE.ANSWER_YES.getValue());
        payload.setTypeShow(typeShow);
        payload.setUserId(userId);
        LOG.info("[createAnswerNoButton] End");
        return new Button(Constant.POSTBACK, Constant.BASIC_BUTTON.ANSWER_NO.getValue()
                , new Gson().toJson(payload));
    }
}

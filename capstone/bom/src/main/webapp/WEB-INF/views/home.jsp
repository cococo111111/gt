<%--
  Created by IntelliJ IDEA.
  User: manlm
  Date: 7/25/2016
  Time: 6:36 PM
  To change this template use File | Settings | File Templates.
--%>
<div class="wrapper wrapper-content animated fadeInRight">
    <div class="row">
        <div class="col-lg-12">
            <div class="text-center m-t-lg">
                <h1>
                    Management data supermarket
                </h1>
                <small>
                    It is an application skeleton for a typical web app. You can use it to quickly bootstrap your webapp
                    projects and dev environment for these projects.
                </small>
            </div>
        </div>
    </div>
</div>

<script>
    $(document).ready(function () {
        setTimeout(function () {
            toastr.options = {
                closeButton: true,
                progressBar: true,
                showMethod: 'slideDown',
                timeOut: 4000
            };
            toastr.success('Responsive Admin Theme', 'Welcome to INSPINIA');

        }, 1300);
    });
</script>

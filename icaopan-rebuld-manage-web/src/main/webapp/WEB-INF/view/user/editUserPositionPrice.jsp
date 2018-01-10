<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/view/include/taglib.jsp" %>
<html>
<head>
    <title>用户持仓成本价调整</title>
    <meta name="decorator" content="default"/>
    <init:init-js/>
</head>
<body>
<div class="portlet box red">
    <div class="portlet-title">
        <div class="caption">
            <i class="fa fa-coffee"></i>用户持仓成本价调整
        </div>
    </div>
    <div class="portlet-body">
        <div class="table-responsive">
            <form id="inputForm" modelAttribute="position" class="form-horizontal">
                <%--<div class="modal-body col-md-9 col-md-offset-2">--%>
                    <input type="hidden" name="id" value="${position.id}"/>
                    <div class="form-group">
                        <label class="col-sm-3 control-label">证券代码</label>
                        <div class="col-sm-4">
                            <label class="control-label">${position.internalSecurityId}</label>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-3 control-label"><label>用 户</label></div>
                        <div class="col-sm-4">
                            <label class="control-label">${position.userName}</label>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-3 control-label"><label>原先成本价</label></div>
                        <div class="col-sm-4">
                            <label class="control-label" id="vorigin">${position.costPrice}</label>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-3 control-label">
                            <label id="corigin">成本价</label>
                        </div>
                        <div class="col-sm-3">
                            <input class="form-control" type="number" name="costPrice" id="costPrice" placeholder="成本价"
                                   aria-describedby="basic-addon1">
                        </div>
                        <div class="col-sm-2">
                            <p style="color: red"><label id="cos"></label></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-md-5 col-md-offset-3">
                            <input type="submit" class="btn btn-danger btn-sm" value="提 交">
                            <button type="button" class="btn btn-default btn-sm" data-dismiss="modal"
                                    onclick="history.go(-1)">返 回
                            </button>
                        </div>
                    </div>
                <%--</div>--%>
            </form>
        </div>
    </div>
</div>
<script>

    $(function () {
        $("#availableChanged").blur(function () {
            var origin = $("#vorigin").text();
            var mark = $("#vmark").val();
            var input = $("#availableChanged").val();
            var temp = (mark == "+" || mark == "" || mark == null) ? new Number(origin) + new Number(input) : new Number(origin) - new Number(input);
            if (temp < 0) {
                $("#ava").text("减配量不能小于可用头寸值");
            } else {
                $("#ava").text("");
            }
            if (mark == null || mark.length == 0) {
                $("#vmark").val("+");
            }
        });
    });

    $(function () {
        $("#inputForm").validate({
            onsubmit: true,// 是否在提交时验证,默认也是true
            rules: {
                availableChanged: {
                    required: true
                },
                costPrice: {
                    required: true
                }

            },
            submitHandler: function (form) {
                $.ajax({
                    "type": 'post',
                    "url": "${ctx}/position/updateUserPositionPrice",
                    "dataType": "json",
                    "data": $("#inputForm").serialize(),
                    "success": function (result) {
                        alertx(result.message);
                        if (result.rescode == "success") {
                            history.go(-1)
                        }
                    }, "error": function (result) {
                        alertx("发生异常,或没权限...");
                    }
                });
            },
        });
    });
</script>
</body>
</html>
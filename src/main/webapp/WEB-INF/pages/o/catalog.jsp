<%@ page import="org.opencloudengine.garuda.belugaservice.db.entity.Resource" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8" %>
<% String menuId = "manage"; %>
<%@include file="top.jsp" %>

<script>
    $(function () {
        $("#app-new-form").validate({
            onkeyup: function (element) {
                var element_id = $(element).attr('id');
                if (this.settings.rules[element_id] && this.settings.rules[element_id].onkeyup != false) {
                    $.validator.defaults.onkeyup.apply(this, arguments);
                }
            },
            rules: {
                id: {
                    idExists: true,
                    lowercase: true,
                    onkeyup: false
                }
            }
        });

        $.validator.addMethod("idExists", function (value, element) {
            var ret = true;
            $.ajax({
                url: "/api/resources/" + value,
                async: false,
                type: "HEAD",
                success: function (response) {
                    ret = false;
                },
                error: function () {
                    ret = true;
                }
            });
            return ret;
        }, "This app id already exists.");

        $.validator.addMethod("lowercase", function (value) {
            // Marathon documentation에서 가져온 정규식.
            // https://mesosphere.github.io/marathon/docs/rest-api.html#post-v2-apps
            return value.match(/^(([a-z0-9]|[a-z0-9][a-z0-9\\-]*[a-z0-9])\\.)*([a-z0-9]|[a-z0-9][a-z0-9\\-]*[a-z0-9])$/);
        }, 'You must use only lowercase letters and numbers. Dot and dash are also allowed, but cannot be used as first or last letter.');
    })

</script>
<div class="container" id="content">
    <div class="row">
        <div class="col-md-12">

            <div class="page-header">
                <h1 id="tables">Create New Resource</h1>
            </div>


            <!--=== Cube-Portfdlio ===-->
            <div class="cube-portfolio container margin-bottom-60">
                <div class="content-xs">
                    <div id="filters-container" class="cbp-l-filters-text content-xs">
                        <div data-filter="*" class="cbp-filter-item-active cbp-filter-item"> All</div>
                        |
                        <div data-filter=".database" class="cbp-filter-item"> 스토리지</div>
                        |
                        <div data-filter=".devops" class="cbp-filter-item"> DevOps</div>
                        |
                        <div data-filter=".network" class="cbp-filter-item"> 네트워크</div>
                        |
                        <div data-filter=".analysis" class="cbp-filter-item"> 데이터 및 분석</div>
                        |
                        <div data-filter=".business" class="cbp-filter-item"> 비즈니스 분석</div>
                    </div>
                    <!--/end Filters Container-->
                </div>

                <br><br>

                <div id="grid-container" class="cbp-l-grid-agency">
                    <c:forEach var="resourceType" items="${allResourceTypes}">
                        <div class="cbp-item ${resourceType.catalog}">
                            <div class="cbp-caption">
                                <div class="cbp-caption-defaultWrap">
                                    <c:choose>
                                        <c:when test="${resourceType.filetype ne null}">
                                            <div style="width: 100%;height: 150px" align="center">
                                                <img style="width: 75px;height: 75px;margin-top: 25px" src="/api/resourcetype/${resourceType.id}/image">
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <img src="/resources/assets/img/main/img26.jpg" alt="">
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="cbp-caption-activeWrap">
                                    <div class="cbp-l-caption-alignCenter">
                                        <div class="cbp-l-caption-body">
                                            <ul class="link-captions">
                                                <li><a href="resources/new?resourceTypeId=${resourceType.id}"><i
                                                        class="rounded-x fa fa-search"></i></a></li>
                                            </ul>
                                            <div class="cbp-l-grid-agency-title">${resourceType.name}</div>
                                            <div class="cbp-l-grid-agency-desc">${resourceType.catalog}</div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
                <!--/end Grid Container-->
            </div>
            <!--=== End Cube-Portfdlio ===-->


        </div>
    </div>
</div>

<%@include file="bottom.jsp" %>
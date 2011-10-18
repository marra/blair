

<%@ page import="blair.TouristicPlace" %>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'touristicPlace.label', default: 'TouristicPlace')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></span>
            <span class="menuButton"><g:link class="list" action="list"><g:message code="default.list.label" args="[entityName]" /></g:link></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="default.edit.label" args="[entityName]" /></h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${touristicPlaceInstance}">
            <div class="errors">
                <g:renderErrors bean="${touristicPlaceInstance}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <g:hiddenField name="id" value="${touristicPlaceInstance?.id}" />
                <g:hiddenField name="version" value="${touristicPlaceInstance?.version}" />
                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="wikiPath"><g:message code="touristicPlace.wikiPath.label" default="Wiki Path" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: touristicPlaceInstance, field: 'wikiPath', 'errors')}">
                                    <g:textField name="wikiPath" value="${touristicPlaceInstance?.wikiPath}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="latitude"><g:message code="touristicPlace.latitude.label" default="Latitude" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: touristicPlaceInstance, field: 'latitude', 'errors')}">
                                    <g:textField name="latitude" value="${touristicPlaceInstance?.latitude}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="longitude"><g:message code="touristicPlace.longitude.label" default="Longitude" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: touristicPlaceInstance, field: 'longitude', 'errors')}">
                                    <g:textField name="longitude" value="${touristicPlaceInstance?.longitude}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="wikiContent"><g:message code="touristicPlace.wikiContent.label" default="Wiki Content" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: touristicPlaceInstance, field: 'wikiContent', 'errors')}">
                                    <g:textField name="wikiContent" value="${touristicPlaceInstance?.wikiContent}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="woeid"><g:message code="touristicPlace.woeid.label" default="Woeid" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: touristicPlaceInstance, field: 'woeid', 'errors')}">
                                    <g:textField name="woeid" value="${touristicPlaceInstance?.woeid}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="isTouristicPlace"><g:message code="touristicPlace.isTouristicPlace.label" default="Is Touristic Place" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: touristicPlaceInstance, field: 'isTouristicPlace', 'errors')}">
                                    <g:textField name="isTouristicPlace" value="${touristicPlaceInstance?.isTouristicPlace}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="city"><g:message code="touristicPlace.city.label" default="City" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: touristicPlaceInstance, field: 'city', 'errors')}">
                                    <g:select name="city.id" from="${blair.City.list()}" optionKey="id" value="${touristicPlaceInstance?.city?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="country"><g:message code="touristicPlace.country.label" default="Country" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: touristicPlaceInstance, field: 'country', 'errors')}">
                                    <g:select name="country.id" from="${blair.Country.list()}" optionKey="id" value="${touristicPlaceInstance?.country?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="name"><g:message code="touristicPlace.name.label" default="Name" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: touristicPlaceInstance, field: 'name', 'errors')}">
                                    <g:textField name="name" value="${touristicPlaceInstance?.name}" />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="region"><g:message code="touristicPlace.region.label" default="Region" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: touristicPlaceInstance, field: 'region', 'errors')}">
                                    <g:select name="region.id" from="${blair.Region.list()}" optionKey="id" value="${touristicPlaceInstance?.region?.id}"  />
                                </td>
                            </tr>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                  <label for="state"><g:message code="touristicPlace.state.label" default="State" /></label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean: touristicPlaceInstance, field: 'state', 'errors')}">
                                    <g:select name="state.id" from="${blair.State.list()}" optionKey="id" value="${touristicPlaceInstance?.state?.id}"  />
                                </td>
                            </tr>
                        
                        </tbody>
                    </table>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" action="update" value="${message(code: 'default.button.update.label', default: 'Update')}" /></span>
                    <span class="button"><g:actionSubmit class="delete" action="delete" value="${message(code: 'default.button.delete.label', default: 'Delete')}" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>

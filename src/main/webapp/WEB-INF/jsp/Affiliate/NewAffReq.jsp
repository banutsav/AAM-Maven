<%-- 
    Document   : newAffReq
    Created on : Mar 6, 2013, 3:15:48 PM
    Author     : submyers
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>
<%@taglib uri="http://struts.apache.org/tags-logic" prefix="logic"%>
<!DOCTYPE html  PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%! String emailAddr;%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <%@include file="/WEB-INF/html/meta.html"%>
        <%@include file="/WEB-INF/html/title.html"%>
        <%@include file="/WEB-INF/html/css.html"%>

        <script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-timepicker-addon.js"></script>
        <link type="text/css" media="all" rel="stylesheet" href="css/jquery-ui-1.10.2.custom.min.css">
        <link type="text/css" media="all" rel="stylesheet" href="css/jquery_datetimepicker.css">

        <style>
            .ui-autocomplete {
                max-height: 100px;
                overflow-y: auto;
                /* prevent horizontal scrollbar */
                overflow-x: hidden;
            }
            /* IE 6 doesn't support max-height
            * we use height instead, but this forces the menu to always be this tall
            */
            * html .ui-autocomplete {
                height: 100px;
            }
            .ui-menu .ui-menu-item { font-size: 12px; }
        </style>

        <script type="text/javascript">
            var nonUgaOpen = false;
            var purposeCount = 0;
            
            function RemovePurpose(purposeId){
                $("#selected_" + purposeId).empty();
                $("#" + purposeId ).removeAttr('disabled');
                $("#" + purposeId ).css("display","block");
                purposeCount--;
                if(purposeCount <= 0){
                    purposeCount = 0;
                    $("#selectedPurposeDiv").html("<br>");
                    $("#selectedPurposeDiv").css("display","none");
                }
            }

            $(document).ready(function(){
            
                $("#submitBtn").click(function(){
                    if($("#firstName").val().toString() == "" || $("#lastName").val().toString() == "" || $("#email").val().toString() == ""){
                        alert("Some required fields were not filled.");
                        return false;
                    }
                    if(purposeCount <= 0){
                        alert("Select at least 1 purpose.")
                        return false;
                    }
                    
                    var purposeIds = "";
                    $(".selected_purpose").each(function(){
                        purposeIds += $(this).attr("value") + " ";
                    });
                    $("#purpose_ids").val(purposeIds);
                    $("#state").val($("#state_selected").val());
                    $("#country").val($("#country_selected").val());
                    $("#action").val("submit");
                    $("#newAffReq").submit();
                });

                $("#backLink").click(function(){
                    $("#action").val("backLink");
                    $("#newAffReq").submit();
                });
               
                $("#cancelBtn").click(function(){
                    $("#action").val("backLink");
                    $("#newAffReq").submit();
                });
               
                $("#addPurpose").click(function(){
                    if($('#purpose option:selected').attr('id') == "undef_purpose") return;
                    $("#selectedPurposeDiv").prepend('<div value="' + $('#purpose option:selected').attr('id') + '" id="selected_' + $('#purpose option:selected').attr('id') + '" class="selected_purpose" ><span style="width:200px;overflow:auto;float:left;white-space: nowrap">' + $('#purpose option:selected').val() + '</span>&nbsp;&nbsp;<input type="button" id="rm_' + $('#purpose option:selected').attr('id') + '" onclick="RemovePurpose(\'' + $('#purpose option:selected').attr('id')+ '\');return false;" value="Remove"></div>');
                    $("#selectedPurposeDiv").css("display","");
                    $('#purpose option:selected').attr('disabled','disabled');
                    $('#purpose option:selected').css("display","none");
                    $('#purpose option:selected').removeAttr("selected");
                    $("#undef_purpose").attr("selected","selected");
                    purposeCount++;
                });
                
                $("#error_dialog").dialog({
                    autoOpen: false,
                    width: "auto",
                    modal: true,
                    resizable: false
                });

            });
        </script>
    </head>
    <body>
        <%@include file="/WEB-INF/jspf/header.jsp"%>
        <%@include file="/WEB-INF/jspf/header_login.jsp"%>
        <html:form styleId="newAffReq" action="NewAffReq.do">
            <html:hidden styleId="action" property="action"/>
            <html:hidden styleId="purpose_ids" property="purpose_ids"/>
            <html:hidden styleId="state" property="state"/>
            <html:hidden styleId="country" property="country"/>
            <bean:define id="emailAddr" name="newAffReqForm" property="email" scope="request" toScope="page" type="String"/>
            <html:hidden styleId="email" property="email" value="<%= emailAddr%>"/>
            <center id="container">
                    <%@include file="/WEB-INF/jspf/messages.jsp"%>

                    <h3>New Affiliate Request</h3>
                    <hr/>
                    <logic:notEmpty name="errorMsg" scope="request">
                        <div>
                            <b style="color: red;"><u><bean:write name="errorMsg" scope="request"/></u></b>
                        </div>
                    </logic:notEmpty>
                    <div style="text-align: left;">
                        <div style="text-align: right;">
                            <a href="./Get.do?action=home">Main Menu</a>
                        </div>
                Please fill out the following New Affiliate Request form.<br>
                (<span style="color: red;">&bull;</span> are required fields)<br>
                <br>
                <table>
                    <tr>
                        <td><b style="color: red;">&bull;</b></td>
                        <td>First Name</td>
                        <td><html:text size="64" maxlength="64" property="firstName" styleId="firstName"/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>Middle Name</td>
                        <td><html:text size="64" maxlength="64" property="middleName" styleId="middleName"/></td>
                    </tr>
                    <tr>
                        <td><b style="color: red;">&bull;</b></td>
                        <td>Last Name</td>
                        <td><html:text size="64" maxlength="64" property="lastName" styleId="lastName"/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>Email</td>
                        <td><bean:write name="newAffReqForm" property="email" scope="request"/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>Phone</td>
                        <td>(<html:text property="pAreaCode" styleId="pAreaCode" size="3" maxlength="3"/>)&nbsp;<html:text property="pPhone1" styleId="pPhone1" size="3" maxlength="3"/> - <html:text property="pPhone2" styleId="pPhone2" size="4" maxlength="4"/></td>
                        <!--td>(<input type="text" id="pAreaCode" size="3" maxlength="3">)&nbsp;<input type="text" id="pPhone1" size="3" maxlength="3"> - <input type="text" id="pPhone2" size="4" maxlength="4"></td-->
                    </tr>
                    <tr>
                        <td></td>
                        <td>Title</td>
                        <td><html:text styleId="title" size="64" maxlength="64" property="title" styleId="title"/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>Business/Institution/Organization</td>
                        <td><html:text size="64" maxlength="64" property="organization" styleId="organization"/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>Department</td>
                        <td><html:text size="64" maxlength="64" property="department" styleId="department"/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>Address Line 1</td>
                        <td><html:text size="64" maxlength="128" property="addr1" styleId="addr1"/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>Address Line 2 </td>
                        <td><html:text size="64" maxlength="128" property="addr2" styleId="addr2"/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>City</td>
                        <td><html:text size="64" maxlength="64" property="city" styleId="city"/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>State</td>
                        <td>
                            <select name="state" id="state_selected">
                                <option value=""></option>
                                <option value="AL">Alabama</option>
                                <option value="AK">Alaska</option>
                                <option value="AZ">Arizona</option>
                                <option value="AR">Arkansas</option>
                                <option value="CA">California</option>
                                <option value="CO">Colorado</option>
                                <option value="CT">Connecticut</option>
                                <option value="DE">Delaware</option>
                                <option value="DC">District of Columbia</option>
                                <option value="FL">Florida</option>
                                <option value="GA">Georgia</option>
                                <option value="HI">Hawaii</option>
                                <option value="ID">Idaho</option>
                                <option value="IL">Illinois</option>
                                <option value="IN">Indiana</option>
                                <option value="IA">Iowa</option>
                                <option value="KS">Kansas</option>
                                <option value="KY">Kentucky</option>
                                <option value="LA">Louisiana</option>
                                <option value="ME">Maine</option>
                                <option value="MD">Maryland</option>
                                <option value="MA">Massachusetts</option>
                                <option value="MI">Michigan</option>
                                <option value="MN">Minnesota</option>
                                <option value="MS">Mississippi</option>
                                <option value="MO">Missouri</option>
                                <option value="MT">Montana</option>
                                <option value="NE">Nebraska</option>
                                <option value="NV">Nevada</option>
                                <option value="NH">New Hampshire</option>
                                <option value="NJ">New Jersey</option>
                                <option value="NM">New Mexico</option>
                                <option value="NY">New York</option>
                                <option value="NC">North Carolina</option>
                                <option value="ND">North Dakota</option>
                                <option value="OH">Ohio</option>
                                <option value="OK">Oklahoma</option>
                                <option value="OR">Oregon</option>
                                <option value="PA">Pennsylvania</option>
                                <option value="RI">Rhode Island</option>
                                <option value="SC">South Carolina</option>
                                <option value="SD">South Dakota</option>
                                <option value="TN">Tennessee</option>
                                <option value="TX">Texas</option>
                                <option value="UT">Utah</option>
                                <option value="VT">Vermont</option>
                                <option value="VA">Virginia</option>
                                <option value="WA">Washington</option>
                                <option value="WV">West Virginia</option>
                                <option value="WI">Wisconsin</option>
                                <option value="WY">Wyoming</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>Country</td>
                        <td>
                            <select size="1" name="country" id="country_selected" onclick="if($('#country').val()!='United States')$('#state').val('');">
                                <option value=""></option>
                                <option value="Afghanistan">Afghanistan</option>
                                <option value="Aland Islands">Aland Islands</option>
                                <option value="Albania">Albania</option>
                                <option value="Algeria">Algeria</option>
                                <option value="American Samoa">American Samoa</option>
                                <option value="Andorra">Andorra</option>
                                <option value="Angola">Angola</option>
                                <option value="Anguilla">Anguilla</option>
                                <option value="Antarctica">Antarctica</option>
                                <option value="Antigua and Barbuda">Antigua and Barbuda</option>
                                <option value="Argentina">Argentina</option>
                                <option value="Armenia">Armenia</option>
                                <option value="Aruba">Aruba</option>
                                <option value="Australia">Australia</option>
                                <option value="Austria">Austria</option>
                                <option value="Azerbaijan">Azerbaijan</option>
                                <option value="Bahamas">Bahamas</option>
                                <option value="Bahrain">Bahrain</option>
                                <option value="Bangladesh">Bangladesh</option>
                                <option value="Barbados">Barbados</option>
                                <option value="Belarus">Belarus</option>
                                <option value="Belgium">Belgium</option>
                                <option value="Belize">Belize</option>
                                <option value="Benin">Benin</option>
                                <option value="Bermuda">Bermuda</option>
                                <option value="Bhutan">Bhutan</option>
                                <option value="Bolivia, Plurinational State of">Bolivia, Plurinational State of</option>
                                <option value="Bonaire, Sint Eustatius and Saba">Bonaire, Sint Eustatius and Saba</option>
                                <option value="Bosnia and Herzegovina">Bosnia and Herzegovina</option>
                                <option value="Botswana">Botswana</option>
                                <option value="Bouvet Island">Bouvet Island</option>
                                <option value="Brazil">Brazil</option>
                                <option value="British Indian Ocean Territory">British Indian Ocean Territory</option>
                                <option value="Brunei Darussalam">Brunei Darussalam</option>
                                <option value="Bulgaria">Bulgaria</option>
                                <option value="Burkina Faso">Burkina Faso</option>
                                <option value="Burundi">Burundi</option>
                                <option value="Cambodia">Cambodia</option>
                                <option value="Cameroon">Cameroon</option>
                                <option value="Canada">Canada</option>
                                <option value="Cape Verde">Cape Verde</option>
                                <option value="Cayman Islands">Cayman Islands</option>
                                <option value="Central African Republic">Central African Republic</option>
                                <option value="Chad">Chad</option>
                                <option value="Chile">Chile</option>
                                <option value="China">China</option>
                                <option value="Christmas Island">Christmas Island</option>
                                <option value="Cocos (Keeling) Islands">Cocos (Keeling) Islands</option>
                                <option value="Colombia">Colombia</option>
                                <option value="Comoros">Comoros</option>
                                <option value="Congo">Congo</option>
                                <option value="Congo, The Democratic Republic of the">Congo, The Democratic Republic of the</option>
                                <option value="Cook Islands">Cook Islands</option>
                                <option value="Costa Rica">Costa Rica</option>
                                <option value="Cote d'Ivoire">Cote d'Ivoire</option>
                                <option value="Croatia">Croatia</option>
                                <option value="Cuba">Cuba</option>
                                <option value="Curacao">Curacao</option>
                                <option value="Cyprus">Cyprus</option>
                                <option value="Czech Republic">Czech Republic</option>
                                <option value="Denmark">Denmark</option>
                                <option value="Djibouti">Djibouti</option>
                                <option value="Dominica">Dominica</option>
                                <option value="Dominican Republic">Dominican Republic</option>
                                <option value="Ecuador">Ecuador</option>
                                <option value="Egypt">Egypt</option>
                                <option value="El Salvador">El Salvador</option>
                                <option value="Equatorial Guinea">Equatorial Guinea</option>
                                <option value="Eritrea">Eritrea</option>
                                <option value="Estonia">Estonia</option>
                                <option value="Ethiopia">Ethiopia</option>
                                <option value="Falkland Islands (Malvinas)">Falkland Islands (Malvinas)</option>
                                <option value="Faroe Islands">Faroe Islands</option>
                                <option value="Fiji">Fiji</option>
                                <option value="Finland">Finland</option>
                                <option value="France">France</option>
                                <option value="French Guiana">French Guiana</option>
                                <option value="French Polynesia">French Polynesia</option>
                                <option value="French Southern Territories">French Southern Territories</option>
                                <option value="Gabon">Gabon</option>
                                <option value="Gambia">Gambia</option>
                                <option value="Georgia">Georgia</option>
                                <option value="Germany">Germany</option>
                                <option value="Ghana">Ghana</option>
                                <option value="Gibraltar">Gibraltar</option>
                                <option value="Greece">Greece</option>
                                <option value="Greenland">Greenland</option>
                                <option value="Grenada">Grenada</option>
                                <option value="Guadeloupe">Guadeloupe</option>
                                <option value="Guam">Guam</option>
                                <option value="Guatemala">Guatemala</option>
                                <option value="Guernsey">Guernsey</option>
                                <option value="Guinea">Guinea</option>
                                <option value="Guinea-Bissau">Guinea-Bissau</option>
                                <option value="Guyana">Guyana</option>
                                <option value="Haiti">Haiti</option>
                                <option value="Heard Island and McDonald Islands">Heard Island and McDonald Islands</option>
                                <option value="Holy See (Vatican City State)">Holy See (Vatican City State)</option>
                                <option value="Honduras">Honduras</option>
                                <option value="Hong Kong">Hong Kong</option>
                                <option value="Hungary">Hungary</option>
                                <option value="Iceland">Iceland</option>
                                <option value="India">India</option>
                                <option value="Indonesia">Indonesia</option>
                                <option value="Iran, Islamic Republic of">Iran, Islamic Republic of</option>
                                <option value="Iraq">Iraq</option>
                                <option value="Ireland">Ireland</option>
                                <option value="Isle of Man">Isle of Man</option>
                                <option value="Israel">Israel</option>
                                <option value="Italy">Italy</option>
                                <option value="Jamaica">Jamaica</option>
                                <option value="Japan">Japan</option>
                                <option value="Jersey">Jersey</option>
                                <option value="Jordan">Jordan</option>
                                <option value="Kazakhstan">Kazakhstan</option>
                                <option value="Kenya">Kenya</option>
                                <option value="Kiribati">Kiribati</option>
                                <option value="Korea, Democratic People's Republic of">Korea, Democratic People's Republic of</option>
                                <option value="Korea, Republic of">Korea, Republic of</option>
                                <option value="Kuwait">Kuwait</option>
                                <option value="Kyrgyzstan">Kyrgyzstan</option>
                                <option value="Lao People's Democratic Republic">Lao People's Democratic Republic</option>
                                <option value="Latvia">Latvia</option>
                                <option value="Lebanon">Lebanon</option>
                                <option value="Lesotho">Lesotho</option>
                                <option value="Liberia">Liberia</option>
                                <option value="Libyan Arab Jamahiriya">Libyan Arab Jamahiriya</option>
                                <option value="Liechtenstein">Liechtenstein</option>
                                <option value="Lithuania">Lithuania</option>
                                <option value="Luxembourg">Luxembourg</option>
                                <option value="Macao">Macao</option>
                                <option value="Macedonia, The Former Yugoslav Republic of">Macedonia, The Former Yugoslav Republic of</option>
                                <option value="Madagascar">Madagascar</option>
                                <option value="Malawi">Malawi</option>
                                <option value="Malaysia">Malaysia</option>
                                <option value="Maldives">Maldives</option>
                                <option value="Mali">Mali</option>
                                <option value="Malta">Malta</option>
                                <option value="Marshall Islands">Marshall Islands</option>
                                <option value="Martinique">Martinique</option>
                                <option value="Mauritania">Mauritania</option>
                                <option value="Mauritius">Mauritius</option>
                                <option value="Mayotte">Mayotte</option>
                                <option value="Mexico">Mexico</option>
                                <option value="Micronesia, Federated States of">Micronesia, Federated States of</option>
                                <option value="Moldova, Republic of">Moldova, Republic of</option>
                                <option value="Monaco">Monaco</option>
                                <option value="Mongolia">Mongolia</option>
                                <option value="Montenegro">Montenegro</option>
                                <option value="Montserrat">Montserrat</option>
                                <option value="Morocco">Morocco</option>
                                <option value="Mozambique">Mozambique</option>
                                <option value="Myanmar">Myanmar</option>
                                <option value="Namibia">Namibia</option>
                                <option value="Nauru">Nauru</option>
                                <option value="Nepal">Nepal</option>
                                <option value="Netherlands">Netherlands</option>
                                <option value="New Caledonia">New Caledonia</option>
                                <option value="New Zealand">New Zealand</option>
                                <option value="Nicaragua">Nicaragua</option>
                                <option value="Niger">Niger</option>
                                <option value="Nigeria">Nigeria</option>
                                <option value="Niue">Niue</option>
                                <option value="Norfolk Island">Norfolk Island</option>
                                <option value="Northern Mariana Islands">Northern Mariana Islands</option>
                                <option value="Norway">Norway</option>
                                <option value="Occupied Palestinian Territory">Occupied Palestinian Territory</option>
                                <option value="Oman">Oman</option>
                                <option value="Pakistan">Pakistan</option>
                                <option value="Palau">Palau</option>
                                <option value="Panama">Panama</option>
                                <option value="Papua New Guinea">Papua New Guinea</option>
                                <option value="Paraguay">Paraguay</option>
                                <option value="Peru">Peru</option>
                                <option value="Philippines">Philippines</option>
                                <option value="Pitcairn">Pitcairn</option>
                                <option value="Poland">Poland</option>
                                <option value="Portugal">Portugal</option>
                                <option value="Puerto Rico">Puerto Rico</option>
                                <option value="Qatar">Qatar</option>
                                <option value="Reunion">Reunion</option>
                                <option value="Romania">Romania</option>
                                <option value="Russian Federation">Russian Federation</option>
                                <option value="Rwanda">Rwanda</option>
                                <option value="Saint Barthelemy">Saint Barthelemy</option>
                                <option value="Saint Helena, Ascension and Tristan da Cunha">Saint Helena, Ascension and Tristan da Cunha</option>
                                <option value="Saint Kitts and Nevis">Saint Kitts and Nevis</option>
                                <option value="Saint Lucia">Saint Lucia</option>
                                <option value="Saint Martin (French part)">Saint Martin (French part)</option>
                                <option value="Saint Pierre and Miquelon">Saint Pierre and Miquelon</option>
                                <option value="Saint Vincent and The Grenadines">Saint Vincent and The Grenadines</option>
                                <option value="Samoa">Samoa</option>
                                <option value="San Marino">San Marino</option>
                                <option value="Sao Tome and Principe">Sao Tome and Principe</option>
                                <option value="Saudi Arabia">Saudi Arabia</option>
                                <option value="Senegal">Senegal</option>
                                <option value="Serbia">Serbia</option>
                                <option value="Seychelles">Seychelles</option>
                                <option value="Sierra Leone">Sierra Leone</option>
                                <option value="Singapore">Singapore</option>
                                <option value="Sint Maarten (Dutch part)">Sint Maarten (Dutch part)</option>
                                <option value="Slovakia">Slovakia</option>
                                <option value="Slovenia">Slovenia</option>
                                <option value="Solomon Islands">Solomon Islands</option>
                                <option value="Somalia">Somalia</option>
                                <option value="South Africa">South Africa</option>
                                <option value="South Georgia and the South Sandwich Islands">South Georgia and the South Sandwich Islands</option>
                                <option value="South Sudan">South Sudan</option>
                                <option value="Spain">Spain</option>
                                <option value="Sri Lanka">Sri Lanka</option>
                                <option value="Sudan">Sudan</option>
                                <option value="Suriname">Suriname</option>
                                <option value="Svalbard and Jan Mayen">Svalbard and Jan Mayen</option>
                                <option value="Swaziland">Swaziland</option>
                                <option value="Sweden">Sweden</option>
                                <option value="Switzerland">Switzerland</option>
                                <option value="Syrian Arab Republic">Syrian Arab Republic</option>
                                <option value="Taiwan, Province of China">Taiwan, Province of China</option>
                                <option value="Tajikistan">Tajikistan</option>
                                <option value="Tanzania, United Republic of">Tanzania, United Republic of</option>
                                <option value="Thailand">Thailand</option>
                                <option value="Timor-Leste">Timor-Leste</option>
                                <option value="Togo">Togo</option>
                                <option value="Tokelau">Tokelau</option>
                                <option value="Tonga">Tonga</option>
                                <option value="Trinidad and Tobago">Trinidad and Tobago</option>
                                <option value="Tunisia">Tunisia</option>
                                <option value="Turkey">Turkey</option>
                                <option value="Turkmenistan">Turkmenistan</option>
                                <option value="Turks and Caicos Islands">Turks and Caicos Islands</option>
                                <option value="Tuvalu">Tuvalu</option>
                                <option value="Uganda">Uganda</option>
                                <option value="Ukraine">Ukraine</option>
                                <option value="United Arab Emirates">United Arab Emirates</option>
                                <option value="United Kingdom">United Kingdom</option>
                                <option value="United States">United States</option>
                                <option value="United States Minor Outlying Islands">United States Minor Outlying Islands</option>
                                <option value="Uruguay">Uruguay</option>
                                <option value="Uzbekistan">Uzbekistan</option>
                                <option value="Vanuatu">Vanuatu</option>
                                <option value="Venezuela, Bolivarian Republic of">Venezuela, Bolivarian Republic of</option>
                                <option value="Viet Nam">Viet Nam</option>
                                <option value="Virgin Islands, British">Virgin Islands, British</option>
                                <option value="Virgin Islands, U.S.">Virgin Islands, U.S.</option>
                                <option value="Wallis and Futuna">Wallis and Futuna</option>
                                <option value="Western Sahara">Western Sahara</option>
                                <option value="Yemen">Yemen</option>
                                <option value="Zambia">Zambia</option>
                                <option value="Zimbabwe">Zimbabwe</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>Zip Code</td>
                        <td><html:text styleId="zip1" size="5" maxlength="5" property="zip1"/>&nbsp;-&nbsp;<html:text styleId="zip2" size="4" maxlength="4" property="zip2"/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>Business Phone</td>
                        <td>(<html:text property="bAreaCode" styleId="bAreaCode" size="3" maxlength="3"/>)&nbsp;<html:text property="bPhone1" styleId="bPhone1" size="3" maxlength="3"/> - <html:text property="bPhone2" styleId="bPhone2" size="4" maxlength="4"/></td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>Fax Number</td>
                        <td>(<html:text property="fAreaCode" styleId="fAreaCode" size="3" maxlength="3"/>)&nbsp;<html:text property="fPhone1" styleId="fPhone1" size="3" maxlength="3"/> - <html:text property="fPhone2" styleId="fPhone2" size="4" maxlength="4"/></td>
                    </tr>
                    <logic:notEmpty name="newAffReqForm" property="purposes" scope="request">
                        <tr>
                            <td style="vertical-align: top;"><b style="color: red;">&bull;</b></td>
                            <td style="vertical-align: top;">Purposes (at least one):</td>
                            <td>
                                <div id="selectedPurposeDiv" style="display: none;">
                                    <br>
                                </div>
                                <select id="purpose">
                                    <option id="undef_purpose" value=""></option>
                                    <logic:iterate id="purpose" name="newAffReqForm" property="purposes" scope="request">
                                        <option id="<bean:write name="purpose" property="ID" scope="page"/>" value="<bean:write name="purpose" property="SHORT_NAME" scope="page"/>"><bean:write name="purpose" property="SHORT_NAME" scope="page"/></option>
                                    </logic:iterate>
                                </select>
                                <input type="button" id="addPurpose" onclick="return false;" value="Add Selected">
                            </td>
                        </tr>
                    </logic:notEmpty>
                </table>
                <br>
                <input type="button" id="submitBtn" onclick="return false;" value="Submit Request">
                &nbsp;&nbsp;&nbsp;
                <input type="button" id="cancelBtn" onclick="return false;" value="Cancel">
            </div>
            <%@include file="/WEB-INF/jspf/footer.jsp"%>
            <div id="error_dialog" title="Unable to submit" style="display: none; font-size: 12px; text-align: center;">
                <div id="errorMsg"></div>
                <input type="button" onclick="$('#error_dialog').dialog('close'); return false;" value="Close">
            </div>
            </center>
        </html:form>
    </body>
</html>

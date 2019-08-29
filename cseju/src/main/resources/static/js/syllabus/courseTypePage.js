$(document).ready(function () {

    var syllabusName = $('#syllabusName').text();

    var loadDataFromXML = function () {
        $.ajax({
            type: 'GET',
            url: '/courseType/Data/' + syllabusName,
            success: function (xmlString) {
                var xmlData = String(xmlString);
                var parser = new DOMParser();
                var xmlDoc = parser.parseFromString(xmlData, "text/xml");

                var courseTypes = xmlDoc.getElementsByTagName('courseType');
                for (var i = 0; i < courseTypes.length; i++) {
                    var courseTypeName = courseTypes[i].getAttribute('name');

                    var row = document.createElement('tr');

                    var cell0 = document.createElement("td");
                    cell0.innerHTML = i + 1;

                    var cellData = $('<h6 id="courseType_' + i + '"></h6>').text(courseTypeName);
                    var cell1 = document.createElement("td");
                    cellData.appendTo(cell1);

                    cellData = $('<button></button>').text('Edit');
                    cellData.attr('class', '');
                    cellData.attr('id', 'courseTypeEdit_' + i);
                    cellData.attr('onclick', 'openCourseStructureDesigner(' + '\'' + syllabusName + '\', \'' + courseTypeName + '\')');

                    var cell2 = document.createElement("td");
                    cellData.appendTo(cell2);

                    cellData = $('<a></a>').text('Delete');
                    cellData.attr('class', '');
                    cellData.attr('id', 'courseTypeDelete_' + i);
                    cellData.attr('role', 'button');
                    cellData.attr('href', '/courseType/' + syllabusName + '/deleteType/' + courseTypeName);

                    var cell3 = document.createElement("td");
                    cellData.appendTo(cell3);


                    row.append(cell0, cell1, cell2, cell3);
                    $('#courseTypesTable tbody').append(row);
                }
            },
            error: function (e) {
                alert("Page Loading Error!!");
                console.log("Error: ", e);
            }
        });
    }

    loadDataFromXML();

    /*to add new course */
    $("#addCourseType").click(function () {
        $("#addTypeFormDivRow").show();
    });

    $("#addCourseTypeForm").submit(function () {
        var newCourseType = $("#newCourseTypeInput").val();
        for (var i = 0; i < $("#courseTypesTable tbody tr").length; i++) {
            var courseTypeName = $("#courseType_".concat(i)).text();
            if (courseTypeName == newCourseType) {
                $("#inputErrorMessage")
                    .text("Already Exists!!!")
                    .show()
                    .fadeOut(2500)
                    .css({
                        "color": "red"
                    });
                return false;
            }
        }
        return true;
    });
})

/*load course structure data*/
function loadCourseStructureDesignData(syllabusName, courseTypeName) {
    var loadCourseStructureDataFromXML = function () {
        $.ajax({
            type: 'GET',
            url: '/courseStructure/Data/' + syllabusName + '/' + courseTypeName,
            success: function (xmlString) {
                var xmlData = String(xmlString);
                var parser = new DOMParser();
                var xmlDoc = parser.parseFromString(xmlData, "text/xml");

                var contentBundles = xmlDoc.getElementsByTagName('contentBundle');
                for (var i = 0; i < contentBundles.length; i++) {
                    var selected = xmlDoc.getElementsByTagName('selected')[i].childNodes[0].nodeValue;

                    var row = $('<tr></tr>');
                    var cell = $('<td></td>')

                    /*TextArea*/
                    var contentDiv = $('<div></div>');
                    contentDiv.attr('class', 'card');
                    contentDiv.attr('id', 'textArea_' + i);

                    var textAreaTitleInput = $('<input/>');
                    textAreaTitleInput.attr('class', '');
                    textAreaTitleInput.attr('id', 'textArea_' + i + 'title');
                    textAreaTitleInput.attr('type', 'text');
                    textAreaTitleInput.attr('value', 'Untitled TextArea');

                    textAreaTitleInput.appendTo(contentDiv);

                    var textAreaTextBody = $('<textArea></textArea>');
                    textAreaTextBody.attr('class', '');
                    textAreaTextBody.attr('value', 'Input Here');

                    textAreaTextBody.appendTo(contentDiv);

                    cell.append(contentDiv);
                    row.append(cell);

                    $('#courseContentTable tbody').append(row);

                    contentDiv = $('<div></div>');
                    contentDiv.attr('class', 'card');
                    contentDiv.attr('id', 'table_' + i);

                    var tableTitleInput = $('<input/>');
                    tableTitleInput.attr('class', '');
                    tableTitleInput.attr('id', 'table_' + i + 'title');
                    tableTitleInput.attr('type', 'text');
                    tableTitleInput.attr('value', 'Untitled Table');

                    tableTitleInput.appendTo(contentDiv);

                    var tableFieldNameTable = $('<table></table>');
                    tableFieldNameTable.attr('class', '');
                    tableFieldNameTable.attr('id', 'tableFieldNameTable_' + i);

                    var fieldNameRow = $('<tr></tr>');
                    var fieldNames = xmlDoc.getElementsByTagName('fields')[i];

                    for (var j = 0; j < fieldNames.childElementCount; j++) {
                        var fieldTd = $('<td></td>');

                        var fieldNameInput = ('<input/>');
                        fieldNameInput.attr('class', '');
                        fieldNameInput.attr('id', 'table_' + i + 'fieldName_' + j);
                        fieldNameInput.attr('value', 'fieldName ' + j);

                        fieldNameInput.appendTo(fieldTd);

                        var deleteFieldNameButton = $('<a></a>').text('Delete');
                        deleteFieldNameButton.attr('class', '');
                        deleteFieldNameButton.attr('role', 'button');
                        deleteFieldNameButton.attr(
                            'href',
                            '/courseStructure/' + syllabusName + '/' + courseTypeName + '/' + i + '/deleteFieldName/' + j
                        );

                        deleteFieldNameButton.appendTo(fieldTd);
                        fieldNameRow.append(fieldTd);
                    }

                    tableFieldNameTable.append(fieldNameRow);

                    console.log('fields ' + fieldNames.childElementCount);

                    /*
                    * <div class=""
                        <div class=""
                             th:id="'table_' + ${rowId.index} + '_field2'">

                            &lt;!&ndash;Table to show the FieldNames of a Table(Content)&ndash;&gt;
                            <table class=""
                                   th:id="'table_' + ${rowId.index} + 'FieldNameTable'">

                                <tbody>
                                <tr>
                                    &lt;!&ndash;For each FieldName in the Table.Fields&ndash;&gt;
                                    <td th:each="contentTableFieldName, colId : ${contentBundle.table.fields}">

                                        <div class=""
                                             th:id="'contentTable' + ${rowId.index} + 'FieldNameDiv' + ${colId.index}">

                                            <div class=""
                                                 th:id="'contentTable' + ${rowId.index} + 'FieldNameInputDiv' + ${colId.index}">

                                                <input class=""
                                                       type="text"
                                                       th:id="'contentTable' + ${rowId.index} + 'FieldName' + ${colId.index}"
                                                       th:value="${contentTableFieldName}"/>
                                            </div>

                                            <div class=""
                                                 th:id="'contentTable' + ${rowId.index} + 'FieldNameDeleteDiv' + ${colId.index}">

                                                &lt;!&ndash;Delete this Field Form the Table(Content)
                                                url: /course_structure/{databaseName}/delete_field_/{contentBundleIndex}/{fieldNameId} &ndash;&gt;
                                                <a class=""
                                                   th:id="'contentTable' + ${rowId.index} + 'FieldNameDeleteButton' + ${colId.index}"
                                                   th:href="@{'/course_structure/' + ${courseStructure.databaseName} + '/delete_field_/' + ${rowId.index} + '/' + ${colId.index}}">Delete
                                                    Field</a>
                                            </div>
                                        </div>
                                    </td>
                                    <td>
                                        <div class=""
                                             th:id="'contentTableAddFieldDiv' + ${rowId.index}">

                                            &lt;!&ndash;Add a new Field in the Table(Content)
                                            url: /course_structure/{databaseName}/add_field_/{contentBundleIndex} &ndash;&gt;
                                            <a class=""
                                               th:id="'contentTableAddFieldButton' + ${rowId.index}"
                                               th:href="@{'/course_structure/' + ${courseStructure.databaseName} + '/add_field_/' + ${rowId.index}}">Add
                                                Field</a>
                                        </div>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
*/

                    //contentDiv.appendTo(cell);


                }
            },
            error: function (e) {
                alert("Page Loading Error!!");
                console.log("Error: ", e);
            }
        });
    }

    loadCourseStructureDataFromXML();
}

/*open and close course input form designer*/
function openCourseStructureDesigner(syllabusName, courseTypeName) {
    $('#courseTypesTableDiv').hide();
    $('#courseStructureDiv').show();

    $('#courseContentTable tbody').empty();
    loadCourseStructureDesignData(syllabusName, courseTypeName);
}

function closeFormDesigner() {
    $('#courseTypesTableDiv').show();
    $('#courseStructureDiv').hide();
};
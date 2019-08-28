
$(document).ready(function() {

    var syllabusName = $('#syllabusName').text();

    var loadDataFromXML = function() {
        $.ajax({
        type: 'GET',
        url:  '/courseType/Data/' + syllabusName,
        success: function(xmlString) {
            var xmlData = String(xmlString);
            var parser = new DOMParser();
            var xmlDoc = parser.parseFromString(xmlData, "text/xml");

            var courseTypes = xmlDoc.getElementsByTagName('courseType');
            for (var i = 0; i < courseTypes.length; i++) {
                var courseTypeName = courseTypes[i].getAttribute('name');

                var row = document.createElement('tr');

                var cell0 = document.createElement("td");
                cell0.innerHTML = i + 1;

                var cell1 = document.createElement("td");
                cell1.innerHTML = courseTypeName;

                var cellData = $('<button></button>').text('Edit');
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
        error : function(e) {
            alert("Page Loading Error!!");
            console.log("Error: ", e);
        }
       });
    }

    loadDataFromXML();

    /*to add new course */
    $("#addCourseType").click(function() {
            $("#addTypeFormDivRow").show();
    });

    $("#addCourseTypeForm").submit(function() {
        var newCourseType = $("#newCourseTypeInput").val();
        for (var i = 0; i < $("#courseTypesTable tbody tr").length; i++) {
            var courseTypeName = $("#courseType_".concat(i)).text();
            if (courseTypeName == newCourseType) {
                $("#inputErrorMessage")
                                .text("Already Exists!!!")
                                .show()
                                .fadeOut(2500)
                                .css({
                                    "color" : "red"
                                });
                return false;
            }
        }
        return true;
    });
})

/*open and close course input form designer*/
function openCourseStructureDesigner(syllabusName, courseTypeName) {
    $('#courseTypesTableDiv').hide();
    $('#courseStructureDiv').show();

    
}

function closeFormDesigner() {
    $('#courseTypesTableDiv').show();
    $('#courseStructureDiv').hide();
};
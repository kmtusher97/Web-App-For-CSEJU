

$(document).ready(function() {
    $("#addCourseType").click(function() {
        $("#addTypeFormDivRow").show();
    });

    $("#addCourseTypeForm").submit(function() {
        var newCourseType = $("#newCourseTypeInput").val();
        for (var i = 0; i < $("#courseTypesTable tbody tr").length; i++) {
            var courseTypeName = $("#courseTypeEdit".concat(i)).text();
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
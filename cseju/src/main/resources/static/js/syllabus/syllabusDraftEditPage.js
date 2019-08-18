
function popupAddCourseForm(yearId, semesterId) {
    var popoverId = 'popover'.concat(semesterId, '_', yearId);
    var dataToggle = '[data-toggle="'.concat(popoverId, '"]');

    var urlPost = '/syllabusDraft/'.concat(
            $('#syllabusTableCaption').text(),
            '/addCourse/',
            yearId,
            '/',
            semesterId
    );
    $('#addCourseForm').attr('action', urlPost);

    $(dataToggle).popover({
        html: true,
        placement: 'auto',
        title: 'Popover Form',
        content: function() {
            return $('#addCourseFormDiv').html();
        }
    });
}
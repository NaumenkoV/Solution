$(function () {
    loadApplicantsIDListByStatus('PASSED');
    loadApplicantsIDListByStatus('EMPLOYED');
    loadApplicantsByStatus('PASSED');
    loadApplicantsByStatus('EMPLOYED');
    if (applicants.length == 0) {
        $('scheduleView').toggle();
        $('nocontent').toggle();
    } else {
        //interviewed
        var interviewedRendered = Mustache.render(pageTemplate, {'data': applicants, 'offering': true});
        var interviewedAccordion = $('.interviewed');
        interviewedAccordion.html(interviewedRendered);

        $(".interviewed").sortable({"axis": "y", items: ".container", handle: ".accordion-section",
            create: function (event) {
                sort($(event.target).children()[0], applicants, false);
            }});
        postRender();
    }
});
employApplicant = function (target) {
    var div = $(target).closest('div.container');
    var applicantID = $(div).find('div.applicant').attr('applicantID');
    var list = {};
    list[applicantID] = {};
    list[applicantID]['status'] = 'EMPLOYED';
    list[applicantID]['rank'] = elementByApplicantID(applicantID)['applicant']['rank'];
    //update statuses at server
    $.ajax({
        async: false,
        url: '/groups/' + groupID + '/applicants',
        dataType: "json",
        contentType: "application/json",
        type: "PUT",
        data: JSON.stringify(list),
        success: function () {
            $(div).find('h3').removeClass('ui-state-highlight');
            $(div).find('h3').find('a').remove();
            $(div).find('h3').append('<span class="employ">[Employed]</span>');
            notifyApplicant($(div).find('div.applicant'));
            elementByApplicantID(applicantID)['applicant']['status'] = 'EMPLOYED';
            $("#dialog").data('content', "Saved!");
            $('#dialog').dialog('open');
        },
        error: function (data) {
            $("#dialog").data('content', "Failed to upload applicants' data to server!");
            $('#dialog').dialog('open');
        }});
};
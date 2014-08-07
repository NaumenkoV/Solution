//on document ready
$(function () {
    loadApplicantsIDListByStatus('PASSED');
    loadApplicantsIDListByStatus('EMPLOYED');
    loadApplicantsByStatus('PASSED');
    loadApplicantsByStatus('EMPLOYED');
    //if no applicants in group
    if (applicants.length == 0) {
        //disable irrelevant buttons
        $('.scheduleView').button({disabled: true});
        $('.nocontent').toggle();
    } else {
        //render applicants with interviews
        var interviewedRendered = Mustache.render(pageTemplate, {'data': applicants, 'offering': true});
        var interviewedAccordion = $('.interviewed');
        interviewedAccordion.html(interviewedRendered);
        //make accordion sortable
        $(".interviewed").sortable({"axis": "y", items: ".applicantContainer", handle: ".accordion-section",
            create: function (event) {
                //'on create': sort items
                sort($(event.target).children()[0], applicants, false);
            }});
    }
    //init accordion, set event listeners
    postRender();
});
//we take him!
employApplicant = function (target) {
    //accordion item
    var div = $(target).closest('div.applicantContainer');
    //get app id from div attr
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
            //change href "make an offer" to span "[employed]"
            var header = $(div).find('h3');
            header.removeClass('ui-state-highlight');
            header.find('a').remove();
            header.append('<span class="employ">[Employed]</span>');
            //notify every applicant, pass divs as targets
            notifyApplicant($(div).find('div.applicant'));
            //change status in memory
            elementByApplicantID(applicantID)['applicant']['status'] = 'EMPLOYED';
            $("#dialog").data('content', "Saved!");
            $('#dialog').dialog('open');
        },
        error: function (data) {
            $("#dialog").data('content', "Failed to upload applicants' data to server!");
            $('#dialog').dialog('open');
        }});
};
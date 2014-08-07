//on document ready
$(function () {
    loadGroupCapacity();
    loadApplicantsIDListByStatus('PASSED');
    loadApplicantsIDListByStatus('NOT_PASSED');
    loadApplicantsByStatus('PASSED');
    loadApplicantsByStatus('NOT_PASSED');
    //if no applicants in group
    if (applicants.length == 0) {
        //disable irrelevant buttons
        $('.scheduleView').button({disabled: true});
        //say sorry, no content
        $('.nocontent').toggle();
    } else {
        //load interview result for each applicant
        applicants.forEach(function (element) {
            loadInterviewResult(element)
        });
        //render applicants with interviews
        var interviewedRendered = Mustache.render(pageTemplate, {'data': applicants, 'rank': true});
        var interviewedAccordion = $('.interviewed');
        interviewedAccordion.html(interviewedRendered);
        //make accordion sortable
        $(".interviewed").sortable({"axis": "y", items: ".applicantContainer", handle: ".accordion-section",
            create: function (event) {
                //'on create': sort items
                sort($(event.target).children()[0], applicants, true);
            }, stop: function (event, ui) {
                //'on stop':highlight top items
                highlight(ui.item);
            }
        });
    }
    //init accordion, set event listeners
    postRender();
});
loadInterviewResult = function (element) {
    $.ajax({
        async: false,
        url: '/interviews/' + element.appointmentID,
        dataType: 'json',
        type: 'GET',
        success: function (interview) {
            element['interview'] = calculateInterviewResult(interview);
        },
        error: function () {
            $("#dialog").data('content', "Failed to load result of the interview by id:" + element.appointmentID);
            $('#dialog').dialog('open');
        }
    });
};
//take interview object received from server, return total points and final comment of interview
calculateInterviewResult = function (interview) {
    var result = {};
    var totalPoints = 0;
    result['finalComment'] = interview['finalComment'];
    $(interview['questions']).each(function (index, value) {
        //total points = sum(each question mark * each question weight)
        totalPoints += +value['mark'] * +value['weight'];
    });
    result['totalPoints'] = totalPoints;
    return result;
};
//highlight accordion items with an appropriate style
highlight = function (item) {
    //siblings and self
    var siblings = $(item).siblings("div.applicantContainer").addBack();
    $(siblings).each(function (index, value) {
        if (index < groupCapacity) {
            $(value).children("h3").addClass("ui-state-highlight");
        } else {
            $(value).children("h3").removeClass("ui-state-highlight");
        }
    });
};
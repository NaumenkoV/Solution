$(function () {
    loadGroupCapacity();
    loadApplicantsIDListByStatus('PASSED');
    loadApplicantsIDListByStatus('NOT_PASSED');
    loadApplicantsByStatus('PASSED');
    loadApplicantsByStatus('NOT_PASSED');
    if (applicants.length == 0) {
        $('scheduleView').toggle();
        $('nocontent').toggle();
    } else {
        applicants.forEach(function (element) {
            loadInterviewResult(element)
        });
//interviewed
        var interviewedRendered = Mustache.render(pageTemplate, {'data': applicants, 'rank': true});
        var interviewedAccordion = $('.interviewed');
        interviewedAccordion.html(interviewedRendered);

        $(".interviewed").sortable({"axis": "y", items: ".container", handle: ".accordion-section",
            create: function (event) {
                sort($(event.target).children()[0], applicants, true);
            }, stop: function (event, ui) {
                highlight(ui.item);
            }
        });
        postRender();
    }
});
loadInterviewResult = function (element) {
    $.ajax({
        async: false,
        url: location.origin + '/interviews/' + element.appointmentID + '/result',
        dataType: 'json',
        type: 'GET',
        success: function (interview) {
            element['interview'] = interview;
        },
        error: function () {
            $("#dialog").data('content', "Failed to load result of the interview by id:" + element.appointmentID);
            $('#dialog').dialog('open');
        }
    });
};
highlight = function (item) {
    var siblings = $(item).siblings("div.container").addBack();
    $(siblings).each(function (index, value) {
        if (index < groupCapacity) {
            $(value).children("h3").addClass("ui-state-highlight");
        } else {
            $(value).children("h3").removeClass("ui-state-highlight");
        }
    });
};
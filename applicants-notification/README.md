#ApplicantsNotification:

resource path: ```/applicantNotification```

	method:  GET  -

	public List<NotificationJSONInfo> postAppointmentIDtoNotifyApplicant(List<NotificationJSONInfo> infoList)
    - returns passes the specified List of NotificationJSONInfo to the queue.


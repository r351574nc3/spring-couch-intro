function(doc) {
    if (doc.docType == 'Event') {
        var startDate = new Date(doc.startTime);
        var startYear = startDate.getFullYear();
        var startMonth = startDate.getMonth();
        var startDay = startDate.getDate();
        emit([
            startYear,
            startMonth,
            startDay
        ]);
    }
}
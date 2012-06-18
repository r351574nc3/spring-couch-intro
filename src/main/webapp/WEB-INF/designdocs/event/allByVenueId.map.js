function(doc) {
    if (doc.docType == 'Event') {
        emit(doc.venueId, {doc});
    }
}
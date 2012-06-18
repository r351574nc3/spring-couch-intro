function(doc) {
    if (doc.docType == 'Event') {
        emit(doc.id, {value:1});
    }
}
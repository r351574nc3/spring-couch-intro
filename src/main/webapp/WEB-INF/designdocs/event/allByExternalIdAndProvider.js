function(doc) {
    if (doc.type == 'Event') {
        emit([doc.externalId, doc.provider], {value:1});
    }
}
function(doc) {
    if (doc.type == 'event') {
        emit(doc.parentId, null);
    }
}
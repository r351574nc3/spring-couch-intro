function(doc) {
    if (doc.type == 'event') {
        emit(doc.parentId, {value:1});
    }
}
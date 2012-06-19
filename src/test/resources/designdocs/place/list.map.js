function(doc) {
	if (doc.docType == 'Place') {
		emit(doc.name, doc);
	}
}
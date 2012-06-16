function(doc) {
	if (doc.type == 'place') {
		emit(doc.name, [ doc.address1, doc.city, doc.state, doc.location]);
	}
}
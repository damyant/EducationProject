package com.university

class NoticeBoard {

	transient springSecurityService

	String noticeHeader
	String fileName


	static constraints = {

	}

	static mapping = {
        noticeHeader column: 'noticeHeader'
        fileName column: "fileName"
	}

	}

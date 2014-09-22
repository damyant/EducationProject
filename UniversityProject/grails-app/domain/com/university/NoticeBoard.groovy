package com.university

class NoticeBoard {

	transient springSecurityService

	String noticeHeader
	String fileName
    Date noticeDate


	static constraints = {

	}

	static mapping = {
        noticeHeader column: 'noticeHeader'
        fileName column: "fileName"
        noticeDate column: "noticeDate"
	}

	}

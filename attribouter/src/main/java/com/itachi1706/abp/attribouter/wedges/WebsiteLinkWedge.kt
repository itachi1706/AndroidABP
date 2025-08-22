package com.itachi1706.abp.attribouter.wedges

class WebsiteLinkWedge(
        url: String, priority: Int
) : LinkWedge(
        id = "website",
        name = "@string/attribouter_title_website",
        url = url,
        icon = "@drawable/attribouter_ic_website",
        priority = priority
)

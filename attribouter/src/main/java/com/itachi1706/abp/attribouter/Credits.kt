package com.itachi1706.abp.attribouter

import com.itachi1706.abp.attribouter.wedges.ContributorWedge
import com.itachi1706.abp.attribouter.wedges.ContributorsWedge
import com.itachi1706.abp.attribouter.wedges.LicenseWedge
import com.itachi1706.abp.attribouter.wedges.LicensesWedge

fun ContributorsWedge.addDefaults() {
    val contributor = "^Contributor"
    addChildren(
        listOf(
            ContributorWedge(
                login = "github:fennifith",
                name = "James Fenn",
                avatarUrl = "https://avatars1.githubusercontent.com/u/13000407",
                task = "^Library Maintainer",
                bio = "Enjoys writing software on loud keyboards. Starts too many projects. Consumes food.",
                websiteUrl = "https://jfenn.me/",
                email = "dev@jfenn.me"
            ).create(lifecycle),
            ContributorWedge(
                login = "github:kevttob",
                name = "Kevin Aguilar",
                avatarUrl = "https://avatars3.githubusercontent.com/u/16209409",
                task = "^Designer",
                websiteUrl = "https://221pxls.com/"
            ).create(lifecycle),
            ContributorWedge(
                login = "github:rroyGit",
                name = "Rupam Roy",
                avatarUrl = "https://avatars2.githubusercontent.com/u/20290568",
                task = contributor
            ).create(lifecycle),
            ContributorWedge(
                login = "github:divadsn",
                name = "David Sn",
                avatarUrl = "https://avatars0.githubusercontent.com/u/28547847",
                task = contributor,
                websiteUrl = "https://www.codebucket.de/"
            ).create(lifecycle),
            ContributorWedge(
                login = "github:gcantoni",
                name = "Giorgio Cantoni",
                avatarUrl = "https://avatars3.githubusercontent.com/u/30368951",
                task = contributor,
                websiteUrl = "https://giorgiocantoni.it/"
            ).create(lifecycle),
            ContributorWedge(
                login = "github:jahirfiquitiva",
                name = "Jahir Fiquitiva",
                avatarUrl = "https://avatars1.githubusercontent.com/u/10360816",
                task = contributor,
                websiteUrl = "https://jahir.dev/"
            ).create(lifecycle)
        )
    )

    requestContributors("github:fennifith/Attribouter")
}

fun LicensesWedge.addDefaults() {
    val apache = "Apache License 2.0"
    val apacheShort = "apache-2.0"
    addChildren(
        listOf(
            LicenseWedge(
                repo = "github:fennifith/Attribouter",
                title = "Attribouter",
                description = "A lightweight \"about screen\" library to allow quick but customizable attribution in Android apps.",
                licenseName = apache,
                licenseKey = apacheShort
            ).create(lifecycle),
            LicenseWedge(
                repo = "gitlab:fennifith/git-rest-wrapper",
                title = "^Git REST Wrapper",
                description = "A 'universal' / normalized API wrapper for git hosting platforms.",
                licenseName = "Mozilla Public License 2.0",
                licenseKey = "mpl-2.0"
            ).create(lifecycle),
            LicenseWedge(
                repo = "github:google/flexbox-layout",
                title = "FlexBox Layout",
                description = "FlexboxLayout is a library that brings similar capabilities to the CSS Flexible Box Layout to Android.",
                licenseName = apache,
                licenseKey = apacheShort
            ).create(lifecycle),
            LicenseWedge(
                repo = "github:bumptech/glide",
                title = "Glide",
                description = "An image loading and caching library for Android focused on smooth scrolling",
                licenseName = "Other",
                websiteUrl = "https://bumptech.github.io/glide/",
                licenseUrl = "https://raw.githubusercontent.com/bumptech/glide/master/LICENSE"
            ).create(lifecycle),
            LicenseWedge(
                title = "Android Open Source Project",
                description = "Android is an open source software stack for a wide range of mobile devices and a corresponding open source project led by Google.",
                licenseName = apache,
                licenseUrl = "https://source.android.com/license",
                websiteUrl = "https://github.com/aosp-mirror",
                licenseKey = apacheShort
            ).create(lifecycle)
        )
    )
}
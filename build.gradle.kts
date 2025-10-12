/*
 * Copyright 2019 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

tasks.register("clean", Delete::class).configure {
    group = "build"
    delete(rootProject.buildDir)
}
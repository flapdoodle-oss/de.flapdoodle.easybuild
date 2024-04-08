# Collection of Ideas

## Cache usage

* This project needs caching. I would propose to use the **maven and gradle cache** as fall back, so that the
  dependencies
  doesn't need to be downloaded again.
* Default cache could be maven, why would we need our own cache?
* Important to know: the caches could be on different locations `GRADLE_USER_HOME` or `~/.gradle`
  and `<settings><localRepository>/path/to/custom/repository</localRepository></settings>` or `~/.m2`

## IDE integration

* one important part is to support at least the most common IDEs like IntelliJ, Eclipse, VSCode, NetBeans. I have no
  idea if it's possible amd simpler to just fake these first to pretend to be a maven project.

## CLI Build without any declaration

### Description

* One powerful and unique feature of maven is, that I don't need to define my plugins in the `pom.xml`. This is awesome
  to keep the build file small as many tasks are only CI/CD related.

### Example

* `jeb package step:jacoco` should be enough to build a project with jacoco coverage. The `step:jacoco` should
  be automatically looked up on github and cached.

## Configs, Profiles?

### Description

* I often have several builds for local, staging, production, native build, jar build,... It would be great if there is
  a way to trigger different builds depending on environment or property configs. maybe like in spring boot with the
  profiles

### Example

* `jeb package -p=native,prod` should build a native image for production

## Build tool should contain embedded java / native compilation

* it would be nice to build this tool to different arch's `universal`, `aarch`, `x86`, `x86_64`, `arm`, `arm64`, `ppc`.
* So that this build tool runs independently of the installed java version. `universal` could be a fallback to the
  installed java version. This would be really nice using `homebrew`.

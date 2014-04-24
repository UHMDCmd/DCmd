grails.project.dependency.resolution = {

   inherits 'global'
   log 'warn'

   repositories {
      mavenCentral()
   }

   dependencies {
      provided('org.codenarc:CodeNarc:0.13') {
         transitive = false
      }
   }
}

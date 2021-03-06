package net.vivin.gradle.versioning

import org.gradle.tooling.BuildException

import java.util.regex.Pattern

class PreRelease {
    String startingVersion
    Pattern pattern = ~/.*$/
    Closure<?> bump

    void validate() {
        if(!startingVersion) {
            throw new BuildException("A starting identifier must be specified in preRelease", null)
        }

        if(!pattern) {
            throw new BuildException("A valid pattern must be specified in preRelease", null)
        }

        String[] components = startingVersion.split(/\./)
        components.each { component ->
            if(!(component ==~ /^[0-9A-Za-z-]+$/)) {
                throw new BuildException("Identifiers must comprise only ASCII alphanumerics and hyphen", null)
            }

            if(component ==~ /^\d+$/ && component ==~ /^0\d+/) {
                throw new BuildException("Numeric identifiers must not include leading zeroes", null)
            }
        }

        if(!bump) {
            throw new BuildException("Bumping scheme for preRelease versions must be specified", null)
        }
    }

    Pattern getPattern() {
        return ~/\d+\.\d+\.\d+-${pattern}/
    }

    void bump(Closure<?> closure) {
        bump = closure
    }
}

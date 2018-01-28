package pl.edu.agh.student.adwin

import spock.lang.Specification

class AdwinTest extends Specification {

    def "testIt"() {
        given:
        List<Double> example = new LinkedList<>(Arrays.asList(3.0, 3.0, 2.8, 2.9, 3.0,2.9,3.1,2.8,3.0,3.2,3.0,
                3.0,9.0,8.9,9.1,9.0, 9.3, 8.9,8.8,9.0,8.9,7.0,9.0,9.8, 9.0, 9.0, 9.0))


        when:
        AdwinZero adwin = AdwinZero.create()
        example.forEach { elem ->
            adwin.addSample(elem)
        }


        then:
        adwin.getDriftDetectedCount()==9
    }
}

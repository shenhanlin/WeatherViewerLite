
package common;

import java.io.Serializable;
import utilities.Debug;

/**
 * Represents a academic organization. An academic organization is normally a 
 * department that offers one or more majors.  Each academic organization will
 * offer classes (like MATHCSSTAT) or exists for external transfers (like GENTRANS). 
 * 
 * @author cjones
 */
public enum AcademicOrganization implements Serializable {
/**
 * Developmental Studies
 */
DEVSTUDY("DEVSTUDY"),
/**
 * Honors
 */
HONORS("HONORS"),
/**
 * Mathematics, Computer Science and Statistics
 */
MATHCSSTAT("MATHCSSTAT"),
/**
 * Exercise Science
 */
EXERSCI ("EXERSCI"),
/**
 * Secondary Education Study
 */
SECEDSTUD("SECEDSTUD"),
/**
 * Nursing
 */
NURSING("NURSING"),
/**
 * Biology
 */
BIOLOGY("BIOLOGY"),
/**
 * Accounting
 */
ACCT("ACCT"),
/**
 * Languages and Cultures
 */
LANGCULT("LANGCULT"),
/**
 * Audiology and Speech Pathology
 */
AUDSLP("AUDSLP"),
/**
 * Psychology
 */
PSYCH("PSYCH"),
/**
 * Extended Programs
 */
EXTEND("EXTEND"),
/**
 * Business Education Information Technology Manager
 */
BUSEDITM("BUSEDITM"),
/**
 * Mass Communication
 */
MASSCOMM("MASSCOMM"),
/**
 * Art Department
 */
ARTDEPT("ARTDEPT"),
/**
 * Information and Tech Management
 */
INSTTECH("INSTTECH"),
/**
 * Chemistry
 */
CHEM("CHEM"),
/**
 * Interdisciplinary studies in the College of Academic Affairs
 */
INTSTUDY("INTSTUDY"), 
/**
 * Elementary and Early Child Education
 */
ELEMED("ELEMED"),
/**
 * Anthropology
 */
ANTHRO("ANTHRO"),
/**
 * Do not know yet
 */
EXCEPT("EXCEPT"),
/**
 * Geography
 */
GEOGRAPHY("GEOGRAPHY"),
/**
 * Marketing
 */
MKTG("MKTG"),
/**
 * Professional Studies
 */
PROFSTUD("PROFSTUD"),
/**
 * Music Department
 */
MUSICDEPT("MUSICDEPT"),
/**
 * Sociology
 */
SOCIOLOGY("SOCIOLOGY"),
/**
 * Management
 */
MGMT("MGMT"),
/**
 * English
 */
ENGLISH("ENGLISH"),
/**
 * Communication Studies
 */
COMMSTUD("COMMSTUD"),
/**
 * History
 */
HISTORY("HISTORY"),
/**
 * First year experience
 */
FYS("FYS"), 
/**
 * Physics Engineering Technology
 */
PHYSENGTEC("PHYSENGTEC"),
/**
 * Philosophy
 */
PHIL("PHIL"),
/**
 * Economic
 */
ECONOMIC("ECONOMIC"),
/**
 * FINANCE
 */
FINANCE("FINANCE"),
/**
 * Political Science
 */
POLISCI("POLISCI"),
/**
 * Interdisciplinary studies in business 
 */
INTERBUS("INTERBUS"),
/**
 * Aerospace Studies
 */
AEROSTUD("AEROSTUD"),
/**
 * General Transfer
 */
GENTRANS("GENTRANS"), 
/**
 * Army
 */
ARMY("ARMY"),

/**
 * Undefined organization
 */
UNDEFINED ("UNDEFINED");

private final String organizationName; 
    
/**
 * 
 * Constructs a new <code> AcademicOrganization </code> instance given the name of the organization.
 * 
 * @param organizationName The name of the organization (MATHCSSTAT).
 */
AcademicOrganization(String organizationName){
        this.organizationName = organizationName.trim();
}
    
     /**
     * Returns the <code>AcademicOrganization</code> with the corresponding organization name.
     *
     * @param organizationName String value of the enumeration.
     * @return Enumeration with that string value.
     */
    public static AcademicOrganization getAcademicOrganization (String organizationName) {
       AcademicOrganization organization = UNDEFINED; // if no matches 

        for (AcademicOrganization e : AcademicOrganization.values ()) {
            if (e.organizationName.equalsIgnoreCase(organizationName.trim())){
                organization = e;
                break;
            }
        }
        return (organization);
    }

    private static final long serialVersionUID = 1L;

    /**
     * Returns the string representation for this <Code>AcademicOrganization</Code>.
     * 
     * @return The String value of the organizationName.
     */
    @Override
    public String toString() {
        return organizationName ;
    }

    /**
     * Gets the value of organizationName. 
     * 
     * @return The String value of organizationName.
     */
    public String getOrganizationName() {
        return organizationName;
    }
    
    /**
     * Tests the <code>AcademicOrganization</code> class. Reviews the getAcademicOrganization(), toString()
     *and getOrganizationName().
     * 
     * @param args Command line arguments.
     */
    public static void main(String[] args){

        Debug.println(AcademicOrganization.getAcademicOrganization("ARMY"));
        Debug.println(AcademicOrganization.getAcademicOrganization("TEST"));
        for (AcademicOrganization a: AcademicOrganization.values()) {
            Debug.println(a);
            Debug.println(a.getOrganizationName());
        }
    }
    
}

package home;

public class ProgramList<P> {
    public P[] gamingApplications;
    public P[] IDEs;
    public P[] workspaceApplications;
    public P notDefined;

    // Constructor
    public ProgramList(P[]  gamingApplications, P[]  IDEs, P[] workspaceApplications){
        this.gamingApplications = gamingApplications;
        this.IDEs = IDEs;
        this.workspaceApplications = workspaceApplications;
        this.notDefined = null;
    }

    // Dummy Constructor for Jackson, idk why this works lol
    public ProgramList(){}

    public P[]  getGamingApplications(){ return this.gamingApplications; }
    public P[]  getIDEs(){
        return this.IDEs;
    }
    public P[]  getWorkspaceApplications(){
        return this.workspaceApplications;
    }


}

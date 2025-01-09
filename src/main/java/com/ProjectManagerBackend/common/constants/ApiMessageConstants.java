package com.ProjectManagerBackend.common.constants;

public class ApiMessageConstants {

    //---------------------------Summaries---------------------------

    public static final String AUTHORIZATION_HEADER_MESSAGE = "Not needed for Swagger UI.";

    public static final String REGISTER = "Register a new user";
    public static final String LOGIN = "Log in with an existing user";

    public static final String SEND_COMMENT = "Send ticket comment";
    public static final String SEND_MESSAGE = "Send project message";

    public static final String GET_COMMENT_BY_TICKET_ID = "Get comments by ticketID";

    public static final String GET_PROJECT_MESSAGES = "Get project messages";
    public static final String GET_PROJECT_TICKETS = "Get project's tickets";

    public static final String ASSIGN_USER_TO_TICKET = "Assign user to ticket";

    public static final String GET_FILTERED_PROJECTS = "Get filtered projects";

    public static final String CREATE_PROJECT = "Create a new project";
    public static final String CREATE_TICKET = "Create project ticket";

    public static final String UPDATE_PROJECT = "Update project info";
    public static final String UPDATE_TICKET_PROGRESS = "Update ticket progress";

    public static final String DELETE_PROJECT = "Delete project";
    public static final String DELETE_TICKET = "Delete ticket";
    public static final String DELETE_COMMENT = "Delete comment";

    public static final String GET_PROJECT = "Get project by ID";
    public static final String GET_TICKET = "Get ticket by ID";

    public static final String REMOVE_USER_FROM_TEAM = "Remove user from project team";

    public static final String SEARCH_PERSONAL_PROJECTS = "Search personal projects";

    public static final String GENERATE_COLLABORATION_CODE = "Generate collaboration code";

    public static final String ACCEPT_COLLABORATION = "Accept collaboration";

    //---------------------------Descriptions---------------------------

    public static final String GET_FILTERED_PROJECTS_DESCRIPTION = "Get projects by email/developmentScope/tag";
    public static final String SEARCH_PERSONAL_PROJECTS_DESCRIPTION = "Search within users' projects by name/team member";

//---------------------------Tags---------------------------

    public static final String USER_TAG_NAME = "A - User Controller";
    public static final String USER_TAG_DESCRIPTION = "Endpoints for managing users";

    public static final String PROJECT_TAG_NAME = "B - Project Controller";
    public static final String PROJECT_TAG_DESCRIPTION = "Endpoints for managing projects";

    public static final String TICKET_TAG_NAME = "C - Ticket Controller";
    public static final String TICKET_TAG_DESCRIPTION = "Endpoints for managing project tickets";

    public static final String Message_TAG_NAME = "D - Message Controller";
    public static final String Message_TAG_DESCRIPTION = "Endpoints for managing project discussion messages";

    public static final String COMMENT_TAG_NAME = "E - Comment Controller";
    public static final String COMMENT_TAG_DESCRIPTION = "Endpoints for managing ticket comments";

}

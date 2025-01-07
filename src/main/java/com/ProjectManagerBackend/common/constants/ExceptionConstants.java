package com.ProjectManagerBackend.common.constants;

public class ExceptionConstants {

    public static final String NOT_FOUND = "%s not found!";
    public static final String ID_NOT_FOUND = "%s with id %s not found!";
    public static final String UNAUTHORIZED_AUTHOR_ONLY_ACTION = "User is not the %1$s author! Only authors can delete %1$ss!";
    public static final String UNAUTHORIZED_ACTION = "User not a project team member! Only team members can %s!";

    public static final String PROJECT_CREATOR_REMOVAL_ERROR = "The project creator cannot be removed from the team!";
    public static final String USER_NOT_PART_OF_PROJECT_TEAM_ERROR = "User is not part of the project team!";
    public static final String USER_WITH_EMAIL_ALREADY_EXISTS = "User with this email already exists!";
    public static final String INCORRECT_USERNAME_OR_PASSWORD = "Incorrect username or password!";
    public static final String COLLABORATION_REQUEST_GENERATION_ERROR = "Error generating collaboration request!";
    public static final String INVALID_COLLABORATION_REQUEST_TOKEN = "Invalid collaboration request token!";

    public static final String PROJECT_WITH_TICKET_ID_NOT_FOUND = "Project with such ticketId does not exist!";
    public static final String TICKET_WITH_COMMENT_ID_NOT_FOUND = "Ticket with such commentId does not exist!";
    public static final String DISCUSSION_WITH_PROJECT_ID_NOT_FOUND = "Discussion with such projectId does not exist!";

    public static final String JWT_TOKEN_EMPTY = "JWT token is required but is null or empty!";

}

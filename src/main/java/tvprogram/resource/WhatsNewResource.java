package tvprogram.resource;

import tvprogram.domain.CommunicationException;
import tvprogram.domain.WeeklyNewProgramme;
import tvprogram.service.ProgrammeService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.Response.Status.OK;
import static javax.ws.rs.core.Response.Status.SERVICE_UNAVAILABLE;
import static javax.ws.rs.core.Response.status;
import static tvprogram.domain.BroadcastFiler.ALL;
import static tvprogram.domain.BroadcastFiler.NOT_SHOWN;

@Path(WhatsNewResource.PROGRAMME_NEW_ENDPOINT)
@Produces(MediaType.APPLICATION_JSON)
public class WhatsNewResource {

    public static final String PROGRAMME_NEW_ENDPOINT = "/programme/new";
    public static final String QUERY_PARAM_SYMBOL = "?";
    public static final String QUERY_PARAM_NOT_SHOWN = "notYetShownOnly";
    private ProgrammeService programmeService;


    @GET
    public Response whatsNew(@QueryParam(QUERY_PARAM_NOT_SHOWN) boolean notYetShownOnly) {
        try {
            List<WeeklyNewProgramme> programs = programmeService.weeklyNewProgrammes(notYetShownOnly ? NOT_SHOWN : ALL);
            return status(OK).entity(programs).build();
        } catch (CommunicationException e) {
            return status(SERVICE_UNAVAILABLE).entity(e.getMessage()).build();
        }
    }

    public WhatsNewResource(ProgrammeService programmeService) {
        this.programmeService = programmeService;
    }

}

package fr.ippon.tatami.controller;

import fr.ippon.tatami.domain.Tweet;
import fr.ippon.tatami.service.TimelineService;
import fr.ippon.tatami.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * Welcome page.
 *
 * @author Julien Dubois
 */
@Controller
public class TimeLineController {

    private final Log log = LogFactory.getLog(TimeLineController.class);

    @Inject
    private UserService userService;

    @Inject
    private TimelineService timelineService;

    @RequestMapping("/timeline")
    public String welcome(HttpServletRequest request) {
        log.warn("User : " + userService.getUserByEmail(request.getRemoteUser()));
        return "timeline";
    }

    @RequestMapping(value = "/rest/tweets", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody
    public Collection<Tweet> listTweets() {
        if (log.isDebugEnabled()) {
            log.debug("REST request to get the tweet list.");
        }
        return timelineService.getTimeline();
    }

    @RequestMapping(value = "/rest/tweets",
            method = RequestMethod.POST,
            consumes = "application/json",
            produces = "application/json")
    public Collection<Tweet> postTweet(@RequestBody String content) {
        if (log.isDebugEnabled()) {
            log.debug("REST request to add tweet : " + content);
        }
        timelineService.postTweet(content);
        return listTweets();
    }
}
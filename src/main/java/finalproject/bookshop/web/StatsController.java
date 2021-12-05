package finalproject.bookshop.web;

import finalproject.bookshop.service.StatsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class StatsController {

  private final StatsService statsService;

  public StatsController(StatsService statsService) {
    this.statsService = statsService;
  }

  @GetMapping("admin/statistics")
  public ModelAndView statistics() {
    ModelAndView modelAndView = new ModelAndView();
    modelAndView.addObject("stats", statsService.getStats());
    modelAndView.setViewName("admin/statistics");
    return modelAndView;
  }

}

package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.i18n.MessagesApi
import play.api.i18n.I18nSupport

object Protocol {
  case class PullData(campaign: String, difficulty: String)
}

@Singleton
class HomeController @Inject()(cc: ControllerComponents)(messages: MessagesApi) extends AbstractController(cc) with I18nSupport {

  import Protocol._
  
  val pullForm = Form(
    mapping(
      "campaign" -> text,
      "difficulty" -> text)
    (PullData.apply)(PullData.unapply))


  def index() = Action { implicit request: Request[AnyContent] =>
    Ok(views.html.index(pullForm))
  }

  def pull() = Action { implicit request =>
      pullForm.bindFromRequest.fold(
       formWithErrors => {
         Ok("nope")
       },
      
      data => {
        Ok(s"${data.campaign} ${data.difficulty}")
      }  
      )
  }
}



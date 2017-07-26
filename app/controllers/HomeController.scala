package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.i18n.MessagesApi
import play.api.i18n.I18nSupport
import helpers.ArkhamChaos

object Protocol {
  case class PullData(campaign: String, difficulty: String)
}

@Singleton
class ChaosController @Inject()(cc: ControllerComponents)(messages: MessagesApi) extends AbstractController(cc) with I18nSupport {

  import Protocol._
  
  val pullForm = Form(
    mapping(
      "campaign" -> text,
      "difficulty" -> text)
    (PullData.apply)(PullData.unapply))


  def index() = Action { implicit request: Request[AnyContent] => 
    val campaign = request.session.get("campaign")
    val difficulty = request.session.get("difficulty")

    Ok(views.html.index(pullForm, campaign, difficulty))
  }

  def pull() = Action { implicit request =>
    import helpers._


    pullForm.bindFromRequest.fold(
       formWithErrors => {
         Ok("nope")
       },
      
      data => {
        val token = ArkhamChaos.pullToken(data.campaign, data.difficulty)
        Redirect("/").flashing("token" -> token).withSession("campaign" -> data.campaign, "difficulty" -> data.difficulty)
      }  
    )
  }
}



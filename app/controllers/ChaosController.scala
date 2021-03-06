package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import play.api.i18n.{ MessagesApi, I18nSupport }
import helpers.ArkhamChaosBag 

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


  def index() = Action { implicit request: Request[AnyContent] => Ok(views.html.index(pullForm)) }

  def reset() = Action { implicit request => Redirect("/").withSession() }

  def pull() = Action { implicit request =>
    
    import helpers._

    pullForm.bindFromRequest.fold(
       formWithErrors => {
          println(formWithErrors)
          Redirect("/").withSession()
          
       },
      
      data => {
        val token = ArkhamChaosBag.pullToken(data.campaign, data.difficulty)
        Redirect("/").flashing("token" -> token).withSession("campaign" -> data.campaign, "difficulty" -> data.difficulty)
      }  
    )
  }
}



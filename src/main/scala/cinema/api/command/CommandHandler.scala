package cinema.api.command

/**
 * Command handlers should use repositories to fetch required data to execute domain functionalities.
 * Domain should contain whole business logic of process.
 */
trait CommandHandler {
  type CommandType <: Command

  def handle(command: CommandType): Unit
}

package com.tauro.scoredcards

import cats.effect.{ExitCode, IO, IOApp}

object Main extends IOApp {
  def run(args: List[String]) =
    ScoredcardsServer.stream[IO].compile.drain.as(ExitCode.Success)
}

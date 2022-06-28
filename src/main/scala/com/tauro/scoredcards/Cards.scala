package com.tauro.scoredcards

import cats.Applicative
import cats.effect.Concurrent
import io.circe.{Decoder, Encoder}
import io.circe.generic.semiauto._
import org.http4s._
import org.http4s.circe._

trait Cards[F[_]]{
  def get(req: Request[F]): F[Cards.Card]
}

object Cards {
  def apply[F[_]](implicit ev: Cards[F]): Cards[F] = ev

  final case class Card(name: String, creditScore: Int, salary: Int)
  object Card {

    implicit val cardDecoder: Decoder[Card] = deriveDecoder[Card]
    implicit def cardEntityDecoder[F[_]: Concurrent]: EntityDecoder[F, Card] =
      jsonOf
    implicit val cardEncoder: Encoder[Card] = deriveEncoder[Card]
    implicit def cardEntityEncoder[F[_]]: EntityEncoder[F, Card] =
      jsonEncoderOf
  }

  def impl[F[_]: Applicative]: HelloWorld[F] = new HelloWorld[F]{
    def get(req: Request[F]): F[Cards.Card] = {
      req.as[Card]
    }
  }
}



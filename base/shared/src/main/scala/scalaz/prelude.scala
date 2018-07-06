package scalaz

import data._

trait BaseDataAliases {
  type Id[X]     = X
}

trait AllFunctions
    extends VoidFunctions
    with data.DisjunctionFunctions
    with data.MaybeFunctions
    with data.KleisliFunctions
    with tc.InvariantFunctorFunctions
    with tc.PhantomFunctions
    with tc.TraversableFunctions

trait AllSyntax
    extends VoidSyntax
    with data.DisjunctionSyntax
    with data.ForallSyntax
    with data.Forall2Syntax
    with data.KleisliSyntax
    with data.MaybeSyntax
    with data.Maybe2Syntax
    with prop.AsSyntax
    with tc.ApplicativeSyntax
    with tc.ApplySyntax
    with tc.BifunctorSyntax
    with tc.BindSyntax
    with tc.ChoiceSyntax
    with tc.CobindSyntax
    with tc.ComonadSyntax
    with tc.ComposeSyntax
    with tc.FoldableSyntax
    with tc.FunctorSyntax
    with tc.InvariantFunctorSyntax
    with tc.PhantomSyntax
    with tc.ProfunctorSyntax
    with tc.StrongSyntax
    with tc.TraversableSyntax
    with tc.EqSyntax
    with tc.OrdSyntax
    with tc.SemigroupSyntax
    with tc.DebugSyntax

trait LowPriority
    extends BaseHierarchy
    with BaseDataAliases

object Scalaz extends LowPriority with AllFunctions with AllSyntax

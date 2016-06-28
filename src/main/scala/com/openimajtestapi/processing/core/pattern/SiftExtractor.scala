package com.OpenImaJTestAPI.processing.core.pattern

import org.openimaj.image.feature.local.engine.DoGSIFTEngine
import org.openimaj.image.ImageUtilities
import java.net.URL
import org.openimaj.feature.local.matcher.BasicMatcher
import org.openimaj.image.feature.local.keypoints.Keypoint
import org.openimaj.image.DisplayUtilities
import org.openimaj.feature.local.matcher.MatchingUtilities
import org.openimaj.image.colour.RGBColour
import org.openimaj.image.processing.resize.ResizeProcessor
import org.openimaj.math.geometry.transforms.AffineTransformModel
import org.openimaj.math.model.fit._
import org.openimaj.feature.local.matcher.consistent._
import org.openimaj.feature.local.matcher._
import org.openimaj.math.geometry.point._
import scala.collection.JavaConverters._

class SiftExtractor {
  val engine = new DoGSIFTEngine();

  def test() {

    val query = ImageUtilities.readMBF(new URL("http://fashionstealer.com/PICTURES/STEALS/our%20legacy/our_legacy_red_yellow_check.jpg")).process(new ResizeProcessor(320));
    val target = ImageUtilities.readMBF(new URL("http://photos.posh24.com/p/1564316/z/hot_news/britney_spears_red_checked_shi.jpg")).process(new ResizeProcessor(320));
    val queryKeypoints = engine.findFeatures(query.flatten());
    val targetKeypoints = engine.findFeatures(target.flatten());

    val fittingModel = new AffineTransformModel(5);
    val ransac = new RANSAC[Point2d, Point2d](fittingModel, 1500, new RANSAC.PercentageInliersStoppingCondition(0.5), true);
    val matcher = new ConsistentLocalFeatureMatcher2d[Keypoint](new FastBasicKeypointMatcher[Keypoint](8), ransac);

    matcher.setModelFeatures(queryKeypoints);
    val matched = matcher.findMatches(targetKeypoints);

    println(matched + " - " + matcher.getMatches().size())
    val consistentMatches = MatchingUtilities.drawMatches(query, target, matcher.getMatches(), RGBColour.RED);
    DisplayUtilities.display(consistentMatches);
  }
}
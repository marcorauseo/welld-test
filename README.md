# welld-test
welld project test for interview


Given a set of N feature points in the plane, determine every line that contains N or more of the points, and
return all points involved. You should also expose a REST API that will allow the caller to:

Add a point to the space
POST /point with body { "x": ..., "y": ... }

Get all points in the space
GET /space

Get all line segments passing through at least N points. Note that a line segment should be a set of
points.
GET /lines/{n}


Remove all points from the space
DELETE /space
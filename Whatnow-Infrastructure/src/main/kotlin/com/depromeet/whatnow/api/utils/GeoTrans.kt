package com.depromeet.whatnow.api.utils

object GeoTrans {
    const val GEO = 0
    const val KATEC = 1
    const val TM = 2
    const val GRS80 = 3
    private val m_Ind = DoubleArray(size = 3)
    private val m_Es = DoubleArray(size = 3)
    private val m_Esp = DoubleArray(size = 3)
    private val src_m = DoubleArray(size = 3)
    private val dst_m = DoubleArray(size = 3)
    private const val EPSLN = 1.0E-10
    private val m_arMajor = DoubleArray(size = 3)
    private val m_arMinor = DoubleArray(size = 3)
    private val m_arScaleFactor = DoubleArray(size = 3)
    private val m_arLonCenter = DoubleArray(size = 3)
    private val m_arLatCenter = DoubleArray(size = 3)
    private val m_arFalseNorthing = DoubleArray(size = 3)
    private val m_arFalseEasting = DoubleArray(size = 3)
    private val datum_params = DoubleArray(size = 3)
    private const val HALF_PI = 1.5707963267948966
    private const val COS_67P5 = 0.3826834323650898
    private const val AD_C = 1.0026

    init {
        m_arScaleFactor[0] = 1.0
        m_arLonCenter[0] = 0.0
        m_arLatCenter[0] = 0.0
        m_arFalseNorthing[0] = 0.0
        m_arFalseEasting[0] = 0.0
        m_arMajor[0] = 6378137.0
        m_arMinor[0] = 6356752.3142
        m_arScaleFactor[1] = 0.9999
        m_arLonCenter[1] = 2.23402144255274
        m_arLatCenter[1] = 0.663225115757845
        m_arFalseNorthing[1] = 600000.0
        m_arFalseEasting[1] = 400000.0
        m_arMajor[1] = 6377397.155
        m_arMinor[1] = 6356078.963342249
        m_arScaleFactor[2] = 1.0
        m_arLonCenter[2] = 2.21661859489671
        m_arLatCenter[2] = 0.663225115757845
        m_arFalseNorthing[2] = 500000.0
        m_arFalseEasting[2] = 200000.0
        m_arMajor[2] = 6377397.155
        m_arMinor[2] = 6356078.963342249
        datum_params[0] = -146.43
        datum_params[1] = 507.89
        datum_params[2] = 681.46
        var tmp = m_arMinor[0] / m_arMajor[0]
        m_Es[0] = 1.0 - tmp * tmp
        m_Esp[0] = m_Es[0] / (1.0 - m_Es[0])
        if (m_Es[0] < 1.0E-5) {
            m_Ind[0] = 1.0
        } else {
            m_Ind[0] = 0.0
        }
        tmp = m_arMinor[1] / m_arMajor[1]
        m_Es[1] = 1.0 - tmp * tmp
        m_Esp[1] = m_Es[1] / (1.0 - m_Es[1])
        if (m_Es[1] < 1.0E-5) {
            m_Ind[1] = 1.0
        } else {
            m_Ind[1] = 0.0
        }
        tmp = m_arMinor[2] / m_arMajor[2]
        m_Es[2] = 1.0 - tmp * tmp
        m_Esp[2] = m_Es[2] / (1.0 - m_Es[2])
        if (m_Es[2] < 1.0E-5) {
            m_Ind[2] = 1.0
        } else {
            m_Ind[2] = 0.0
        }
        src_m[0] = m_arMajor[0] * mlfn(
            e0fn(m_Es[0]),
            e1fn(
                m_Es[0],
            ),
            e2fn(m_Es[0]),
            e3fn(m_Es[0]),
            m_arLatCenter[0],
        )
        dst_m[0] = m_arMajor[0] * mlfn(
            e0fn(m_Es[0]),
            e1fn(
                m_Es[0],
            ),
            e2fn(m_Es[0]),
            e3fn(m_Es[0]),
            m_arLatCenter[0],
        )
        src_m[1] = m_arMajor[1] * mlfn(
            e0fn(m_Es[1]),
            e1fn(
                m_Es[1],
            ),
            e2fn(m_Es[1]),
            e3fn(m_Es[1]),
            m_arLatCenter[1],
        )
        dst_m[1] = m_arMajor[1] * mlfn(
            e0fn(m_Es[1]),
            e1fn(
                m_Es[1],
            ),
            e2fn(m_Es[1]),
            e3fn(m_Es[1]),
            m_arLatCenter[1],
        )
        src_m[2] = m_arMajor[2] * mlfn(
            e0fn(m_Es[2]),
            e1fn(
                m_Es[2],
            ),
            e2fn(m_Es[2]),
            e3fn(m_Es[2]),
            m_arLatCenter[2],
        )
        dst_m[2] = m_arMajor[2] * mlfn(
            e0fn(m_Es[2]),
            e1fn(
                m_Es[2],
            ),
            e2fn(m_Es[2]),
            e3fn(m_Es[2]),
            m_arLatCenter[2],
        )
    }

    private fun D2R(degree: Double): Double {
        return degree * Math.PI / 180.0
    }

    private fun R2D(radian: Double): Double {
        return radian * 180.0 / Math.PI
    }

    private fun e0fn(x: Double): Double {
        return 1.0 - 0.25 * x * (1.0 + x / 16.0 * (3.0 + 1.25 * x))
    }

    private fun e1fn(x: Double): Double {
        return 0.375 * x * (1.0 + 0.25 * x * (1.0 + 0.46875 * x))
    }

    private fun e2fn(x: Double): Double {
        return 0.05859375 * x * x * (1.0 + 0.75 * x)
    }

    private fun e3fn(x: Double): Double {
        return x * x * x * 0.011393229166666666
    }

    private fun mlfn(e0: Double, e1: Double, e2: Double, e3: Double, phi: Double): Double {
        return e0 * phi - e1 * Math.sin(2.0 * phi) + e2 * Math.sin(4.0 * phi) - e3 * Math.sin(6.0 * phi)
    }

    private fun asinz(value: Double): Double {
        var value = value
        if (Math.abs(value) > 1.0) {
            value = (if (value > 0.0) 1 else -1).toDouble()
        }
        return Math.asin(value)
    }

    fun convert(srctype: Int, dsttype: Int, in_pt: GeoTransPoint): GeoTransPoint {
        val tmpPt = GeoTransPoint()
        val out_pt = GeoTransPoint()
        if (srctype == 0) {
            tmpPt.x = D2R(in_pt.x)
            tmpPt.y = D2R(in_pt.y)
        } else {
            tm2geo(srctype, in_pt, tmpPt)
        }
        if (dsttype == 0) {
            out_pt.x = R2D(tmpPt.x)
            out_pt.y = R2D(tmpPt.y)
        } else {
            geo2tm(dsttype, tmpPt, out_pt)
        }
        return out_pt
    }

    fun geo2tm(dsttype: Int, in_pt: GeoTransPoint, out_pt: GeoTransPoint) {
        transform(0, dsttype, in_pt)
        val delta_lon: Double = in_pt.x - m_arLonCenter[dsttype]
        val sin_phi = Math.sin(in_pt.y)
        val cos_phi = Math.cos(in_pt.y)
        var al: Double
        var con: Double
        if (m_Ind[dsttype] != 0.0) {
            al = cos_phi * Math.sin(delta_lon)
            Math.abs(Math.abs(al) - 1.0)
        } else {
            al = 0.0
            val x = 0.5 * m_arMajor[dsttype] * m_arScaleFactor[dsttype] * Math.log((1.0 + al) / (1.0 - al))
            con = Math.acos(cos_phi * Math.cos(delta_lon) / Math.sqrt(1.0 - al * al))
            if (in_pt.y < 0.0) {
                con *= -1.0
                val var5 = m_arMajor[dsttype] * m_arScaleFactor[dsttype] * (con - m_arLatCenter[dsttype])
            }
        }
        al = cos_phi * delta_lon
        con = al * al
        val c = m_Esp[dsttype] * cos_phi * cos_phi
        val tq = Math.tan(in_pt.y)
        val t = tq * tq
        con = 1.0 - m_Es[dsttype] * sin_phi * sin_phi
        val n = m_arMajor[dsttype] / Math.sqrt(con)
        val ml = m_arMajor[dsttype] * mlfn(
            e0fn(m_Es[dsttype]),
            e1fn(
                m_Es[dsttype],
            ),
            e2fn(m_Es[dsttype]),
            e3fn(m_Es[dsttype]),
            in_pt.y,
        )
        out_pt.x =
            m_arScaleFactor[dsttype] * n * al * (1.0 + con / 6.0 * (1.0 - t + c + con / 20.0 * (5.0 - 18.0 * t + t * t + 72.0 * c - 58.0 * m_Esp[dsttype]))) + m_arFalseEasting[dsttype]
        out_pt.y =
            m_arScaleFactor[dsttype] * (ml - dst_m[dsttype] + n * tq * con * (0.5 + con / 24.0 * (5.0 - t + 9.0 * c + 4.0 * c * c + con / 30.0 * (61.0 - 58.0 * t + t * t + 600.0 * c - 330.0 * m_Esp[dsttype])))) + m_arFalseNorthing[dsttype]
    }

    fun tm2geo(srctype: Int, in_pt: GeoTransPoint, out_pt: GeoTransPoint) {
        val tmpPt = GeoTransPoint(in_pt.x, in_pt.y)
        val max_iter = 6
        var con: Double
        var phi: Double
        if (m_Ind[srctype] != 0.0) {
            con = Math.exp(in_pt.x / (m_arMajor[srctype] * m_arScaleFactor[srctype]))
            phi = 0.5 * (con - 1.0 / con)
            val temp: Double = m_arLatCenter[srctype] + tmpPt.y / (m_arMajor[srctype] * m_arScaleFactor[srctype])
            val h = Math.cos(temp)
            val con = Math.sqrt((1.0 - h * h) / (1.0 + phi * phi))
            out_pt.y = asinz(con)
            if (temp < 0.0) {
                out_pt.y *= -1.0
            }
            if (phi == 0.0 && h == 0.0) {
                out_pt.x = m_arLonCenter[srctype]
            } else {
                out_pt.x = Math.atan(phi / h) + m_arLonCenter[srctype]
            }
        }
        tmpPt.x -= m_arFalseEasting[srctype]
        tmpPt.y -= m_arFalseNorthing[srctype]
        con = (src_m[srctype] + tmpPt.y / m_arScaleFactor[srctype]) / m_arMajor[srctype]
        phi = con
        var i = 0
        while (true) {
            var sin_phi = (
                con + e1fn(m_Es[srctype]) * Math.sin(2.0 * phi) - e2fn(
                    m_Es[srctype],
                ) * Math.sin(4.0 * phi) + e3fn(m_Es[srctype]) * Math.sin(6.0 * phi)
                ) / e0fn(
                m_Es[srctype],
            ) - phi
            phi += sin_phi
            if (Math.abs(sin_phi) <= EPSLN || i >= max_iter) {
                if (Math.abs(phi) < 1.5707963267948966) {
                    sin_phi = Math.sin(phi)
                    val cos_phi = Math.cos(phi)
                    val tan_phi = Math.tan(phi)
                    val c = m_Esp[srctype] * cos_phi * cos_phi
                    val cs = c * c
                    val t = tan_phi * tan_phi
                    val ts = t * t
                    val cont = 1.0 - m_Es[srctype] * sin_phi * sin_phi
                    val n = m_arMajor[srctype] / Math.sqrt(cont)
                    val r = n * (1.0 - m_Es[srctype]) / cont
                    val d: Double = tmpPt.x / (n * m_arScaleFactor[srctype])
                    val ds = d * d
                    out_pt.y =
                        phi - n * tan_phi * ds / r * (0.5 - ds / 24.0 * (5.0 + 3.0 * t + 10.0 * c - 4.0 * cs - 9.0 * m_Esp[srctype] - ds / 30.0 * (61.0 + 90.0 * t + 298.0 * c + 45.0 * ts - 252.0 * m_Esp[srctype] - 3.0 * cs)))
                    out_pt.x =
                        m_arLonCenter[srctype] + d * (1.0 - ds / 6.0 * (1.0 + 2.0 * t + c - ds / 20.0 * (5.0 - 2.0 * c + 28.0 * t - 3.0 * cs + 8.0 * m_Esp[srctype] + 24.0 * ts))) / cos_phi
                } else {
                    out_pt.y = 1.5707963267948966 * Math.sin(tmpPt.y)
                    out_pt.x = m_arLonCenter[srctype]
                }
                transform(srctype, 0, out_pt)
                return
            }
            ++i
        }
    }

    fun getDistancebyGeo(pt1: GeoTransPoint, pt2: GeoTransPoint): Double {
        val lat1 = D2R(pt1.y)
        val lon1 = D2R(pt1.x)
        val lat2 = D2R(pt2.y)
        val lon2 = D2R(pt2.x)
        val longitude = lon2 - lon1
        val latitude = lat2 - lat1
        val a = Math.pow(
            Math.sin(latitude / 2.0),
            2.0,
        ) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(longitude / 2.0), 2.0)
        return 12753.0 * Math.atan2(Math.sqrt(a), Math.sqrt(1.0 - a))
    }

    fun getDistancebyKatec(pt1: GeoTransPoint, pt2: GeoTransPoint): Double {
        var pt1: GeoTransPoint = pt1
        var pt2: GeoTransPoint = pt2
        pt1 = convert(1, 0, pt1)
        pt2 = convert(1, 0, pt2)
        return getDistancebyGeo(pt1, pt2)
    }

    fun getDistancebyTm(pt1: GeoTransPoint, pt2: GeoTransPoint): Double {
        var pt1: GeoTransPoint = pt1
        var pt2: GeoTransPoint = pt2
        pt1 = convert(2, 0, pt1)
        pt2 = convert(2, 0, pt2)
        return getDistancebyGeo(pt1, pt2)
    }

    private fun getTimebySec(distance: Double): Long {
        return Math.round(3600.0 * distance / 4.0)
    }

    fun getTimebyMin(distance: Double): Long {
        return Math.ceil((getTimebySec(distance) / 60L).toDouble()).toLong()
    }

    private fun transform(srctype: Int, dsttype: Int, point: GeoTransPoint) {
        if (srctype != dsttype) {
            if (srctype != 0 || dsttype != 0) {
                geodetic_to_geocentric(srctype, point)
                if (srctype != 0) {
                    geocentric_to_wgs84(point)
                }
                if (dsttype != 0) {
                    geocentric_from_wgs84(point)
                }
                geocentric_to_geodetic(dsttype, point)
            }
        }
    }

    private fun geodetic_to_geocentric(type: Int, p: GeoTransPoint): Boolean {
        var Longitude: Double = p.x
        var Latitude: Double = p.y
        val Height: Double = p.z
        if (Latitude < -1.5707963267948966 && Latitude > -1.5723671231216914) {
            Latitude = -1.5707963267948966
        } else if (Latitude > 1.5707963267948966 && Latitude < 1.5723671231216914) {
            Latitude = 1.5707963267948966
        } else if (Latitude < -1.5707963267948966 || Latitude > 1.5707963267948966) {
            return true
        }
        if (Longitude > Math.PI) {
            Longitude -= 6.283185307179586
        }
        val Sin_Lat = Math.sin(Latitude)
        val Cos_Lat = Math.cos(Latitude)
        val Sin2_Lat = Sin_Lat * Sin_Lat
        val Rn = m_arMajor[type] / Math.sqrt(1.0 - m_Es[type] * Sin2_Lat)
        val X = (Rn + Height) * Cos_Lat * Math.cos(Longitude)
        val Y = (Rn + Height) * Cos_Lat * Math.sin(Longitude)
        val Z = (Rn * (1.0 - m_Es[type]) + Height) * Sin_Lat
        p.x = X
        p.y = Y
        p.z = Z
        return false
    }

    private fun geocentric_to_geodetic(type: Int, p: GeoTransPoint) {
        val X: Double = p.x
        val Y: Double = p.y
        val Z: Double = p.z
        var Latitude = 0.0
        var At_Pole = false
        val Longitude: Double
        val Height: Double
        if (X != 0.0) {
            Longitude = Math.atan2(Y, X)
        } else if (Y > 0.0) {
            Longitude = 1.5707963267948966
        } else if (Y < 0.0) {
            Longitude = -1.5707963267948966
        } else {
            At_Pole = true
            Longitude = 0.0
            if (Z > 0.0) {
                Latitude = 1.5707963267948966
            } else {
                if (Z >= 0.0) {
                    Latitude = 1.5707963267948966
                    Height = -m_arMinor[type]
                    return
                }
                Latitude = -1.5707963267948966
            }
        }
        val W2 = X * X + Y * Y
        val W = Math.sqrt(W2)
        val T0 = Z * 1.0026
        val S0 = Math.sqrt(T0 * T0 + W2)
        val Sin_B0 = T0 / S0
        val Cos_B0 = W / S0
        val Sin3_B0 = Sin_B0 * Sin_B0 * Sin_B0
        val T1 = Z + m_arMinor[type] * m_Esp[type] * Sin3_B0
        val Sum = W - m_arMajor[type] * m_Es[type] * Cos_B0 * Cos_B0 * Cos_B0
        val S1 = Math.sqrt(T1 * T1 + Sum * Sum)
        val Sin_p1 = T1 / S1
        val Cos_p1 = Sum / S1
        val Rn = m_arMajor[type] / Math.sqrt(1.0 - m_Es[type] * Sin_p1 * Sin_p1)
        Height = if (Cos_p1 >= 0.3826834323650898) {
            W / Cos_p1 - Rn
        } else if (Cos_p1 <= -0.3826834323650898) {
            W / -Cos_p1 - Rn
        } else {
            Z / Sin_p1 + Rn * (m_Es[type] - 1.0)
        }
        if (!At_Pole) {
            Latitude = Math.atan(Sin_p1 / Cos_p1)
        }
        p.x = Longitude
        p.y = Latitude
        p.z = Height
    }

    private fun geocentric_to_wgs84(p: GeoTransPoint) {
        p.x += datum_params[0]
        p.y += datum_params[1]
        p.z += datum_params[2]
    }

    private fun geocentric_from_wgs84(p: GeoTransPoint) {
        p.x -= datum_params[0]
        p.y -= datum_params[1]
        p.z -= datum_params[2]
    }
}

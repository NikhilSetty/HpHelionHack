package org.hphelion.heliohelp.Interfaces;

import java.sql.Connection;

/**
 * Created by NJere on 4/22/2015.
 */
public interface IDbAccess {
    Connection getConnection();
}

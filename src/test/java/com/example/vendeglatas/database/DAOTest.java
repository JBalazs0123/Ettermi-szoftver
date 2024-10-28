package com.example.vendeglatas.database;
import com.example.vendeglatas.modules.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.*;
import java.text.SimpleDateFormat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class DAOTest {

    private DAO dao;
    private Connection mockConnection;
    private PreparedStatement mockPreparedStatement;
    private Statement mockStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws SQLException, ClassNotFoundException {
        dao = spy(new DAO());
        mockConnection = mock(Connection.class);
        mockPreparedStatement = mock(PreparedStatement.class);
        mockStatement = mock(Statement.class);
        mockResultSet = mock(ResultSet.class);

        // Mock the DAO() method to return the mock connection
        doReturn(mockConnection).when(dao).DAO();
    }

    @Test
    void testSaveProduct() throws Exception {
        Product product = new Product(1, "Category", null, "ProductName", 100);

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        dao.saveProduct(product);

        verify(mockPreparedStatement, times(1)).setNull(1, Types.INTEGER);
        verify(mockPreparedStatement, times(1)).setString(2, product.getCategory());
        verify(mockPreparedStatement, times(1)).setString(3, null);
        verify(mockPreparedStatement, times(1)).setString(4, product.getName());
        verify(mockPreparedStatement, times(1)).setInt(5, product.getPrice());
        verify(mockPreparedStatement, times(1)).executeUpdate();
        verify(mockPreparedStatement, times(1)).close();
        verify(mockConnection, times(1)).close();
    }

    @Test
    void testDeleteProduct() throws Exception {
        String productName = "ProductName";
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        dao.deleteProduct(productName);

        verify(mockPreparedStatement, times(1)).executeUpdate();
        verify(mockPreparedStatement, times(1)).close();
        verify(mockConnection, times(1)).close();
    }

    @Test
    void testSaveBill() throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Bill bill = new Bill(1, 2, 300, sdf.parse("2023-01-01 12:00:00"), "Cash");

        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        dao.saveBill(bill);

        verify(mockConnection).prepareStatement("INSERT INTO szamla VALUES (?, ?, ?, ?, ?)");
        verify(mockPreparedStatement).setNull(1, java.sql.Types.INTEGER);
        verify(mockPreparedStatement).setInt(2, bill.getOrderId());
        verify(mockPreparedStatement).setInt(3, bill.getPrice());
        verify(mockPreparedStatement).setString(4, "2023-01-01 12:00:00");
        verify(mockPreparedStatement).setString(5, bill.getPayMethod());
        verify(mockPreparedStatement).executeUpdate();
        verify(mockPreparedStatement).close();
        verify(mockConnection).close();
    }


    @Test
    void testDeletOrder() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        dao.deletOrder(1);

        verify(mockConnection).prepareStatement("DELETE FROM rendeles WHERE rendelesazonosito='1'");
        verify(mockPreparedStatement).executeUpdate();
        verify(mockPreparedStatement).close();
        verify(mockConnection).close();
    }

    @Test
    void testDeleteIncludesOnlyOneItem() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        dao.deleteIncludesOnlyOneItem(1, 1);

        verify(mockConnection, times(2)).prepareStatement(anyString());
        verify(mockPreparedStatement, times(2)).executeUpdate();
        verify(mockPreparedStatement, times(2)).close();
        verify(mockConnection).close();
    }

    @Test
    void testDeleteIncludes() throws SQLException {
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        dao.deleteIncludes(1);

        verify(mockConnection).prepareStatement("DELETE FROM tartalmaz WHERE rendelesAzonosito='1'");
        verify(mockPreparedStatement).executeUpdate();
        verify(mockPreparedStatement).close();
        verify(mockConnection).close();
    }

    @Test
    void testSaveInclude() throws SQLException {
        Include include = new Include(1, 1, 1);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        dao.saveInclude(include);

        verify(mockConnection).prepareStatement("INSERT INTO tartalmaz VALUES (?, ?, ?)");
        verify(mockPreparedStatement).setInt(1, include.getOrderId());
        verify(mockPreparedStatement).setInt(2, include.getProductId());
        verify(mockPreparedStatement).setInt(3, include.getAmount());
        verify(mockPreparedStatement).executeUpdate();
        verify(mockPreparedStatement).close();
        verify(mockConnection).close();
    }

    @Test
    void testSaveOrder() throws SQLException {
        Order order = new Order(1, 1, 1, 1);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        dao.saveOrder(order);

        verify(mockConnection).prepareStatement("INSERT INTO rendeles VALUES (?, ?, ?, ?)");
        verify(mockPreparedStatement).setInt(1, order.getId());
        verify(mockPreparedStatement).setInt(2, order.getTableNumber());
        verify(mockPreparedStatement).setInt(3, order.getEmployeId());
        verify(mockPreparedStatement).setInt(4, order.getNumberOfProduct());
        verify(mockPreparedStatement).executeUpdate();
        verify(mockPreparedStatement).close();
        verify(mockConnection).close();
    }

    @Test
    void testSaveEmploye() throws SQLException {
        Employe employe = new Employe("RestaurantName", "EmployeName", "Password", "Post");
        when(mockConnection.createStatement()).thenReturn(mock(Statement.class));

        dao.saveEmploye(employe);

        verify(mockConnection).createStatement();
        verify(mockConnection.createStatement()).execute("INSERT INTO alkalmazott(nev, alkalmazottNev, jelszo, beosztas)" +
                " VALUES ('RestaurantName', 'EmployeName', 'Password', 'Post')");
        verify(mockConnection.createStatement()).close();
        verify(mockConnection).close();
    }
}

package proba;

import domain.User;
import dataAccess.HibernateDataAccess;
import businessLogic.BLFacadeImplementation;
import exceptions.UserAlreadyExistsException;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class Main {

    public static void main(String[] args) {
        // Crear una instancia de HibernateDataAccess (asegúrate de tener el HibernateSessionFactory configurado)
        HibernateDataAccess dataAccess = new HibernateDataAccess();

        // Crear una instancia de la capa de lógica de negocio
        BLFacadeImplementation blFacade = new BLFacadeImplementation(dataAccess);

        // Crear sesión
        Session session = null;

        try {
            // Iniciar una nueva sesión de Hibernate
            session = ((Session) dataAccess).getSessionFactory().openSession();
            session.beginTransaction();

            // Datos de un nuevo usuario
            String name = "John Doe";
            String email = "johndoe@example.com";
            String password = "password123";

            // Intentar registrar el usuario a través de la lógica de negocio
            blFacade.registerUser(name, email, password);

            // Hacer commit a la transacción
            session.getTransaction().commit();

            System.out.println("Usuario registrado con éxito!");
        } catch (UserAlreadyExistsException e) {
            // Excepción cuando el correo ya está registrado
            System.out.println("Error: El usuario con este correo electrónico ya existe.");
            e.printStackTrace();
        } catch (HibernateException e) {
            // Excepción de Hibernate (problemas de base de datos)
            e.printStackTrace();
        } finally {
            if (session != null) {
                session.close(); // Asegurarse de cerrar la sesión
            }
        }
    }
}

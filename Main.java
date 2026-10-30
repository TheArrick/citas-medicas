import java.util.ArrayList;
import java.util.List;

// ============================================================
// RESERVA DE CITAS MEDICAS - Patrones de diseño
//   Creacional:     SINGLETON (Agenda)
//   Estructural:    ADAPTER   (WhatsAppAdapter)
//   Comportamiento: OBSERVER  (Agenda avisa a sus observadores)
// ============================================================

// ---------- OBSERVER: interfaz de quien quiere ser avisado ----------
interface Observador {
    void notificar(String mensaje);
}

// ---------- SINGLETON + OBSERVER (Subject) ----------
// SINGLETON: la clínica tiene UNA sola agenda. El constructor es privado
// y getInstancia() siempre devuelve el mismo objeto.
// OBSERVER: la agenda guarda una lista de observadores y les avisa
// cada vez que se reserva una cita, sin saber qué hace cada uno.
class Agenda {
    private static Agenda instancia;
    private final List<String> citas = new ArrayList<>();
    private final List<Observador> observadores = new ArrayList<>();

    private Agenda() { }

    public static Agenda getInstancia() {
        if (instancia == null) instancia = new Agenda();
        return instancia;
    }

    public void suscribir(Observador o) {
        observadores.add(o);
    }

    public void reservar(String paciente, String fecha) {
        citas.add(paciente + " - " + fecha);
        for (Observador o : observadores) {
            o.notificar("Cita reservada: " + paciente + " el " + fecha);
        }
    }

    public List<String> getCitas() {
        return citas;
    }
}

// ---------- OBSERVER concreto: la recepción registra la cita ----------
class Recepcion implements Observador {
    public void notificar(String mensaje) {
        System.out.println("[Recepcion] " + mensaje);
    }
}

// ---------- ADAPTER ----------
// WhatsAppApi es un servicio EXTERNO con un método distinto (sendMessage)
// que no podemos cambiar. Nuestro sistema solo entiende Observador.notificar().
class WhatsAppApi {
    public void sendMessage(String text) {
        System.out.println("[WhatsApp] " + text);
    }
}

// El adaptador implementa la interfaz que espera nuestro sistema (Observador)
// y por dentro "traduce" la llamada al método de WhatsAppApi.
class WhatsAppAdapter implements Observador {
    private final WhatsAppApi api = new WhatsAppApi();

    public void notificar(String mensaje) {
        api.sendMessage(mensaje);
    }
}

// ---------- PROGRAMA PRINCIPAL ----------
public class Main {
    public static void main(String[] args) {
        Agenda agenda = Agenda.getInstancia();               // SINGLETON
        System.out.println("Misma agenda? " + (agenda == Agenda.getInstancia()));

        agenda.suscribir(new Recepcion());                   // OBSERVER
        agenda.suscribir(new WhatsAppAdapter());             // ADAPTER como observador

        agenda.reservar("Ana Perez", "05/11/2026 09:00");
        agenda.reservar("Luis Rojas", "05/11/2026 10:00");

        System.out.println("Citas en agenda: " + agenda.getCitas());
    }
}

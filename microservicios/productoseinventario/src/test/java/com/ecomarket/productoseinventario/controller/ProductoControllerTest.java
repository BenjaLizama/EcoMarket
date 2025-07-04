import com.ecomarket.productoseinventario.controller.ProductoController;
import com.ecomarket.productoseinventario.model.Producto;
import com.ecomarket.productoseinventario.services.CategoriaService;
import com.ecomarket.productoseinventario.services.ProductoService;
import com.ecomarket.productoseinventario.services.StockService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductoControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProductoService productoService;

    @Mock
    private StockService stockService;

    @Mock
    private CategoriaService categoriaService;

    @InjectMocks
    private ProductoController productoController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productoController).build();
    }

    @Test
    void testListarProductos_emptyList_returnsNoContent() throws Exception {
        when(productoService.findAll()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/productos"))
                .andExpect(status().isNoContent());
    }

    @Test
    void testListarProductos_nonEmptyList_returnsOk() throws Exception {
        Producto producto = new Producto();
        producto.setNombreProducto("Producto Test");

        when(productoService.findAll()).thenReturn(Collections.singletonList(producto));

        mockMvc.perform(get("/api/v1/productos")
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$[0].nombreProducto").value("Producto Test"));
    }

}

package model.filtros;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Este filtro procesa las excepciones que se arrojan hacia arriba y generan un mecanismo estandar de salida de la excepci&oacute;n
 * @generated
 */
@WebFilter(filterName = "FiltroExcepciones", urlPatterns = {"/webresources/*"})
public class FiltroExcepciones implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //no realiza ninguna accion
    }

    /**
      * procesa las excepciones y las arroja hacia la capa superior
      */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            chain.doFilter(request, response);
        } catch (Exception e) {
            response.setContentType("application/json;charset=UTF-8");
            ((HttpServletResponse) response).setStatus(500);
            try (PrintWriter out = response.getWriter()) {
                out.append("{\"mensaje\":\""+procesarTexto(obtenerUltimaExcepcion(e).getMessage())+"\"}");
            }
        }
    }

    @Override
    public void destroy() {
        //no realiza ninguna accion
    }

    /**
      * convierte los saltos de linea y tabuladores para que se interprete en el javascript
      */
    private String procesarTexto(String texto){
        return texto.replaceAll("\n", "\\\\n")
                .replaceAll("\t", "\\\\t");
    }

    /**
      * Metodo recursivo que obtiene la excepcion original
      */
    private Throwable obtenerUltimaExcepcion(Throwable e){
        if(e.getCause()!=null){
            return obtenerUltimaExcepcion(e.getCause());
        }else{
            return e;
        }
    }
    
}
